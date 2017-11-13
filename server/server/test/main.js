process.env.NODE_ENV = 'test';
//Require the dev-dependencies
let chai = require('chai');
let chaiHttp = require('chai-http');
var server = require('../app');

let should = chai.should();

chai.use(chaiHttp);


require('./auth')(chai, server, should)