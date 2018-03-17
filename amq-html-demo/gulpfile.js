var gulp               = require('gulp');             
var fs                 = require('fs');
var es                 = require('event-stream');
var path               = require('path');
var _                  = require('lodash');
var uglify             = require('gulp-uglify'); 
var sass               = require('gulp-sass');
var cleanCSS           = require('gulp-clean-css');
var rename             = require('gulp-rename'); 
var autoprefixer       = require('gulp-autoprefixer');
var include            = require('gulp-include');
var notify             = require("gulp-notify");
var imagemin           = require("gulp-imagemin"); 
var livereload         = require('gulp-livereload');
var sourcemaps         = require('gulp-sourcemaps');
var webserver          = require('gulp-webserver');
var clean              = require('gulp-clean');
var replace            = require('gulp-string-replace');
var yn                 = require('yn');

var srcPath            = 'templates/src/';            // Path to the source files
var distPath           = 'templates/dist/';            // Path to the distribution files
var paths = {
    webroot: "./wwwroot/",
    node_modules: "./node_modules/"
};

// Paths that gulp should watch
var watchPaths        = {
    scripts:     [
        srcPath+'assets/js/*.js',
        srcPath+'assets/js/**/*.js'
    ],
    images:     [
        srcPath+'assets/img/**'
    ],
    sass:         [
        srcPath+'assets/sass/*.scss',
        srcPath+'assets/sass/**/*.scss'
    ],
    fonts:      [
        srcPath+'assets/fonts/**'
    ],
    html:          [
        srcPath+'**/*.html',
        srcPath+'**/*.php'
    ]
};

gulp.task('clean', function () {
    // return gulp
    //     .src(distPath, { read: false })
    //     .pipe(clean());
});

// Task for sass files
gulp.task('sass', ['clean'], function () {
    return gulp
        .src(srcPath + 'assets/sass/styles.scss')
        .pipe(include())
        .pipe(sass())
        .on("error", notify.onError({ message: "Error: <%= error.message %>", title: "Error running sass task" }))
        .pipe(autoprefixer({ browsers: ['> 1%', 'last 2 versions'], cascade: false }))
        .on("error", notify.onError({ message: "Error: <%= error.message %>", title: "Error running sass task" }))
        .pipe(cleanCSS({ keepBreaks: false }))
        .on("error", notify.onError({ message: "Error: <%= error.message %>", title: "Error running sass task" }))
        .pipe(rename({ suffix: '.min' }))
        .pipe(gulp.dest(distPath + 'assets/css'));
});

// Javscript task
gulp.task('scripts', ['clean'], function(){
    return gulp
        .src(srcPath + 'assets/js/*.js')
        .pipe(include())
        .pipe(sourcemaps.init())
        .pipe(uglify())
        .on("error", notify.onError({ message: "Error: <%= error.message %>", title: "Error running scripts task" }))
        .pipe(rename({ suffix: '.min' }))
        .pipe(sourcemaps.write('maps'))
        .pipe(gulp.dest(distPath + 'assets/js'));
});

// Font task
gulp.task('fonts', ['clean'], function () {
    return gulp
        .src([srcPath + 'assets/fonts/**'])
        .pipe(gulp.dest(distPath + 'assets/fonts'));
});

// HTML task
gulp.task('html', ['clean'], function () {
    return gulp
        .src([srcPath + '*.html'])
        .pipe(include())
        .pipe(replace('wss://localhost:443', process.env.AMQ_URL || 'wss://messaging-maas-hguerrero.6a63.fuse-ignite.openshiftapps.com:443'))
        .pipe(replace('myaddress', process.env.DESTINATION || 'myqueue'))
        .pipe(replace('user', process.env.MQ_USERNAME || 'user'))
        .pipe(replace('password', process.env.MQ_PASSWORD || 'password'))
        .pipe(replace('loopback', process.env.LOOPBACK || 'true'))
        .on("error", notify.onError({ message: "Error: <%= error.message %>", title: "Error running html task" }))
        .pipe(gulp.dest(distPath));
});

// Images task
gulp.task('images', ['clean'], function () {
    return gulp
        .src(srcPath + 'assets/img/**')
        .pipe(imagemin())
        .on("error", notify.onError({ message: "Error: <%= error.message %>", title: "Error running image task" }))
        .pipe(gulp.dest(distPath + 'assets/img'));
});

// Copy libs task
gulp.task("copy:libs", ['clean'], function () {
    var assets = {
        css: [
            paths.node_modules + "patternfly/dist/css/**",
            paths.node_modules + "patternfly/node_modules/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css"
        ],
        js: [
            paths.node_modules + "patternfly/dist/js/**",
            paths.node_modules + "patternfly/node_modules/jquery/dist/**",
            paths.node_modules + "patternfly/node_modules/bootstrap/dist/js/**",
            paths.node_modules + "patternfly/node_modules/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js",
            paths.node_modules + "rhea/dist/rhea.js"
        ],
        fonts: [
            paths.node_modules + "patternfly/dist/fonts/**"
        ]
    };

    _(assets).forEach(function(assets, type) {
        gulp
            .src(assets)
            .pipe(gulp.dest(distPath + 'assets/' + type));
    });

    return assets;
});

// Watch task
gulp.task('watch', function() {
    gulp.watch(watchPaths.scripts, ['scripts']);
    gulp.watch(watchPaths.images, ['images']);
    gulp.watch(watchPaths.sass, ['sass']);
    gulp.watch(watchPaths.html, ['html']);
    gulp.watch(watchPaths.fonts, ['fonts']);

    livereload.listen();
    gulp.watch(distPath + '**').on('change', livereload.changed);
    return;
});

gulp.task('build', ['copy:libs', 'scripts', 'images', 'sass', 'fonts', 'html'], function() {
    return;
});

gulp.task('serve', ['build'], function () {
    return gulp.src(distPath)
      .pipe(webserver({
        host: process.env.HOSTNAME || 'localhost',
        port: 8080,
        livereload: yn(process.env.LIVERELOAD, { default: false })
      }));
  });

gulp.task('serve:watch', ['watch', 'serve'], function(){
    return;
});

// Default task
gulp.task('default', ['build', 'watch']);
