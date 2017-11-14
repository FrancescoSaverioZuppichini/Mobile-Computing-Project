module.exports = function (chai, server, should) {
  it('it should return a specific user, if extis', (done) => {
    chai.request(server)
      .get('/api/users/')
      .end(function (err, res) {
        res.should.have.status(200);
        done();
      });
  });
}
