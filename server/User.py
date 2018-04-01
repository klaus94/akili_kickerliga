#!/ usr/binenv python
# -*- coding : utf -8 -*-

class User(object):
	def __init__(self, name, points, imageURL):
		self.name = name
		self.points = points
		self.imageURL = imageURL

	def serialize(self):
		return { 
			'name': self.name,
			'points': self.points,
			'imageURL': self.imageURL
		}