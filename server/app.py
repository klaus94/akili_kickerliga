#!flask/bin/python
from flask import Flask, jsonify, abort, make_response, request, send_file
from flask_cors import CORS
from db import DB
from random import randrange
from User import User
import os

app = Flask(__name__)
CORS(app)

db = DB()


@app.route('/users', methods=['GET'])
def get_users():
	return jsonify([u.serialize() for u in db.getUsers()])

@app.route('/users', methods=['POST'])
def create_user():
	json = request.get_json()

	print json

	name = ""
	if 'name' in json:
		name = json['name']
	try:
		user = User(name, 0, "")
		db.addUser(user)
	except Exception as e:
		print e
		return make_response(jsonify({'error': 'user has no name'}), 400)
	
	return jsonify(True), 201

@app.route('/images/new/<int:recepe_id>', methods=['POST'])
def add_image(recepe_id):
	origFileName = request.files['image'].filename
	ending = origFileName.split('.')[-1]
	imgName = "img_" + str(randrange(0, 100000000)) + "." + ending 
	filePath = "images/" + imgName
	request.files['image'].save(filePath)

	db.addImage(recepe_id, imgName)
	return jsonify('success'), 201


@app.route('/images/<int:image_id>', methods=['GET'])
def get_image(image_id):
	fileName = db.get_image_file_name(image_id)
	path = "images/" + fileName

	return send_file(path, mimetype='image/jpeg')

@app.errorhandler(404)
def not_found(error):
	return make_response(jsonify({'error': 'Not found'}), 404)


if __name__ == '__main__':
	if not os.path.exists("images"):
		os.makedirs("images")
	app.run(host='0.0.0.0', debug=True)