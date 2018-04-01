#!/ usr/binenv python
# -*- coding : utf -8 -*-

import sqlite3
from User import User
from Game import Game

class DB(object):
	def __init__(self):
		self.openConnection()

		# create user-database
		sqlCommand = """CREATE TABLE IF NOT EXISTS users (
			name TEXT PRIMARY KEY,
			points INTEGER,
			imageURL TEXT);"""
		self.cursor.execute(sqlCommand)
		self.connection.commit()

		# create games-database
		sqlCommand = """CREATE TABLE IF NOT EXISTS games (
			id INTEGER PRIMARY KEY,
			game_date INTEGER,
			player1_team1 TEXT,
			player2_team1 TEXT,
			player1_team2 TEXT,
			player2_team2 TEXT,
			goals_team1 INTEGER,
			goals_team2 INTEGER);"""
		self.cursor.execute(sqlCommand)
		self.connection.commit()

		self.closeConnection()

	def addUser(self, user):
		self.openConnection()

		sqlCommand = """INSERT INTO users (name, points, imageURL) VALUES (?, ?, ?);"""
		self.cursor.execute(sqlCommand, (user.name, user.points, user.imageURL))
		self.connection.commit()
		self.closeConnection()

		return True

	def getUsers(self):
		users = []

		self.openConnection()
		self.cursor.execute("SELECT * FROM users")
		usersList = self.cursor.fetchall()
		for u in usersList:
			users.append(User(u[0], u[1], u[2]))
		self.closeConnection()

		return users

	def openConnection(self):
		self.connection = sqlite3.connect("kickerliga.db")
		self.cursor = self.connection.cursor()

	def closeConnection(self):
		self.connection.close()

