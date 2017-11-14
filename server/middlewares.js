var jwt = require('jsonwebtoken')

function JWTProtected(req, res, next) {
  try {
    if (!req.token) throw { message: 'Token not provided' }

    jwt.verify(req.token, process.env.TOKEN_SECRET);

    var decoded = jwt.decode(req.token, { complete: true })
    
    req.user = decoded.payload.data
    
    next()
  } catch (err) {
    err.status = 401
    next(err)
  }
}

module.exports = {
  JWTProtected
}
