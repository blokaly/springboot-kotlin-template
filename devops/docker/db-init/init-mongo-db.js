/*
 * Initialisation script for MongoDB.
 * Run automatically by MongoDB docker image.
 */

var db = new Mongo('127.0.0.1:27017').getDB('admin');
db.auth("root", "@Dm1n");

db.createUser({
	user : "template",
	pwd : "password",
	roles : [ { role: "readWrite", db: "springbootkotlin"} ]
})
