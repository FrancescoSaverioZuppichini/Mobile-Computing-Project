var jwt = require('jsonwebtoken')

function JWTProtected(req, res, next) {
  try {
    if (!req.token) throw Error('Token not provided')

    jwt.verify(req.token, 'alessia');

    var decoded = jwt.decode(req.token, { complete: true })
    
    req.user = decoded.payload.data
    
    next()
  } catch (err) {
    next(err)
  }
}

module.exports = {
  JWTProtected
}
