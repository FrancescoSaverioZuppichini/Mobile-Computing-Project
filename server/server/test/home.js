module.exports = function (chai, server, should) {
    it('it should return 200', (done) => {
      chai.request(server)
        .get('/api/')
        .end(function (err, res) {
          res.should.have.status(200);
          done();
        });
    });
  }
  