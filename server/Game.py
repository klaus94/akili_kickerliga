#!/ usr/binenv python
# -*- coding : utf -8 -*-

class Game(object):
	def __init__(self, 
		Id,
		date,
		player1_team1,
		player2_team1,
		player1_team2,
		player2_team2,
		goalsTeam1,
		goalsTeam2):

		self.id = Id
		self.date = date
		self.player1_team1 = player1_team1
		self.player2_team1 = player2_team1
		self.player1_team2 = player1_team2
		self.player2_team2 = player2_team2
		self.goalsTeam1 = goalsTeam1
		self.goalsTeam2 = goalsTeam2

	def serialize(self):
		return { 
			'id': self.id,
			'date': self.date,
			'player1_team1': self.player1_team1,
			'player2_team1': self.player2_team1,
			'player1_team2': self.player1_team2,
			'player2_team2': self.player2_team2,
			'goalsTeam1': self.goalsTeam1,
			'goalsTeam2': self.goalsTeam2
		}