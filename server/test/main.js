process.env.NODE_ENV = 'test';
//Require the dev-dependencies
var chai = require('chai');
var chaiHttp = require('chai-http');
var mongoose = require('mongoose')
var server = require('../app');

let should = chai.should();
// delete mongoose cache or mocia watcher will fail
delete mongoose.connection.models['User'];

chai.use(chaiHttp);


require('./auth')(chai, server, should)
require('./users')(chai, server, should)