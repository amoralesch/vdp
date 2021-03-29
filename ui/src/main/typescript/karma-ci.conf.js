// Karma configuration file, see link for more information
// https://karma-runner.github.io/1.0/config/configuration-file.html

process.env.CHROMIUM_BIN = require('path').join(__dirname, '../../../target/chromium/chrome')

module.exports = function (config) {
  config.set({
    basePath: '',
    frameworks: [
      'jasmine',
      '@angular-devkit/build-angular'
    ],
    plugins: [
      require('karma-jasmine'),
      require('karma-chrome-launcher'),
      require('karma-junit-reporter'),
      require('karma-coverage-istanbul-reporter'),
      require('@angular-devkit/build-angular/plugins/karma')
    ],
    port: 12012,
    colors: false,
    logLevel: config.LOG_INFO,
    autoWatch: false,
    customLaunchers: {
      CustomChromium: {
        base: 'ChromiumHeadless',
        flags: [
          '--no-sandbox',
          '--disable-gpu',
          '--disable-features=VizDisplayCompositor'
        ]
      }
    },
    browsers: ['CustomChromium'],
    singleRun: true,
    restartOnFileChange: false,
    client: {
      clearContext: false // leave Jasmine Spec Runner output visible in browser
    },
    reporters: ['dots', 'junit'],
    junitReporter: {
      outputFile: require('path').join(__dirname, '../../../target/surefire-reports/TEST-ui.xml')
    },
    coverageIstanbulReporter: {
      dir: require('path').join(__dirname, '../../../target/coverage'),
      reports: ['lcovonly', 'text-summary'],
      fixWebpackSourcePaths: true
    }
  });
};
