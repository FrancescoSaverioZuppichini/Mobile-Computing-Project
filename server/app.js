var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var mongoose = require('mongoose')
var jwt = require('jsonwebtoken');
var bearerToken = require('express-bearer-token');
var middlewares = require('./middlewares')

const routers = require('./routes/index')

// connect app to our backend TODO put it in a module
DB_URL = process.env.NODE_ENV == 'test' ? 'mongodb://localhost/mobile_computing_test' : 'mongodb://localhost/mobile_computing'
mongoose.connect(DB_URL, {
    useMongoClient: true
  })
  .then(() => {
    // drop if imn test
    if (process.env.NODE_ENV == 'test') mongoose.connection.db.dropDatabase();
    console.log(`mongo connected at ${DB_URL}`)
  })

mongoose.Promise = global.Promise;

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
  extended: false
}));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/auth', routers.auth);
app.use('/api', bearerToken());
app.use('/api', middlewares.JWTProtected)
app.use('/api', routers.home);
app.use('/api/users', routers.users);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function (err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};
  // render the error page
  res.status(err.status || 500);
  console.error(err)
  res.send(err);
});

module.exports = app;
