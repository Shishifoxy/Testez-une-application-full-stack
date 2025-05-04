module.exports = {
  module: {
    rules: [
      {
        test: /\.[jt]s$/,
        exclude: /(node_modules|\.spec\.ts$)/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env'],
            plugins: ['istanbul']
          }
        }
      }
    ]
  }
};
