var express = require('express');
var router = express.Router();

const User = require('../models/User')
/* GET users listing. */
router.get('/', async (req, res, next) => {
  const users = await User.find()
  res.send(users);
});

module.exports = router;
