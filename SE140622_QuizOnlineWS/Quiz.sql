CREATE DATABASE QuizOnline
GO

USE QuizOnline
GO

CREATE TABLE tbl_Account(
	id INT PRIMARY KEY IDENTITY(1,1),
	email VARCHAR(50) NOT NULL,
	name NVARCHAR(30) NOT NULL,
	password VARCHAR(100) NOT NULL,
	isAdmin BIT DEFAULT 0,
	isBanned BIT DEFAULT 0
)

CREATE TABLE tbl_Subject(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(50),
	status BIT DEFAULT 1,
	quizTime TINYINT,
	noOfQuestion TINYINT,
)

CREATE TABLE tbl_Question(
	id INT PRIMARY KEY IDENTITY(1,1),
	content NVARCHAR(300) NOT NULL,
	createdDate DATE DEFAULT GETDATE(),
	lastModifiedDate DATETIME,
	status BIT DEFAULT 1,
	createdUser INT FOREIGN KEY REFERENCES tbl_Account(id),
	lastModifiedUser INT FOREIGN KEY REFERENCES tbl_Account(id),
	subjectId INT FOREIGN KEY REFERENCES tbl_Subject(id)
)

CREATE TABLE tbl_Answer(
	id INT PRIMARY KEY IDENTITY(1,1),
	content NVARCHAR(300) NOT NULL,
	isRight BIT,
	questionId INT FOREIGN KEY REFERENCES tbl_Question(id)
)

CREATE TABLE tbl_Quiz(
	id INT PRIMARY KEY IDENTITY(1,1),
	userId INT FOREIGN KEY REFERENCES tbl_Account(id),
	subjectId INT FOREIGN KEY REFERENCES tbl_Subject(id),
	createdDate DATETIME DEFAULT GETDATE(),
	submittedDate DATETIME,
	correctQues TINYINT,
	totalQues TINYINT
)

CREATE TABLE tbl_QuizDetail(
	quizId INT FOREIGN KEY REFERENCES tbl_Quiz(id),
	questionId INT FOREIGN KEY REFERENCES tbl_Question(id),
	userChoice INT,
	isCorrect BIT
	PRIMARY KEY(questionId, quizId)
)

CREATE TABLE tbl_QuestionUpdateHistory(
	questionId INT FOREIGN KEY REFERENCES tbl_Question(id),
	questionContent NVARCHAR(300),
	answer01 NVARCHAR(300),
	answer02 NVARCHAR(300),
	answer03 NVARCHAR(300),
	answer04 NVARCHAR(300),
	correctAnswer NVARCHAR(300),
	createdDate DATETIME DEFAULT GETDATE()
)

INSERT INTO tbl_Category(name)
VALUES('Software Engineering')

INSERT INTO tbl_Category(name)
VALUES('International Business')

INSERT INTO tbl_Category(name)
VALUES('Japanese')

INSERT INTO tbl_Subject(name, quizTime, noOfQuestion, categoryId)
VALUES('DBI202', 10, 10, 1)

INSERT INTO tbl_Subject(name, quizTime, noOfQuestion, categoryId)
VALUES('JPD111', 5, 10, 3)

INSERT INTO tbl_Subject(name, quizTime, noOfQuestion, categoryId)
VALUES('OSG202', 10, 10, 1)

select * from tbl_Question a where a.id in 
(select top 10 id from tbl_Question WHERE subjectId = 1 order by newid())

SELECT * FROM tbl_Question
  WHERE (ABS(CAST(
  (BINARY_CHECKSUM(*) *
  RAND()) as int)) % 100) <= 10 AND subjectId = 1

