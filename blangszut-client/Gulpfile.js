var gulp = require('gulp'),
    g = require('gulp-load-plugins')();

gulp.task('less', function() {
   gulp.src('client/less/main.less')
       .pipe(g.less({
           paths: [
               'client/less',
               'client/lib'
           ]
       }))
       .pipe(gulp.dest('client/css'));
});

gulp.task('watch', function() {
   gulp.watch('client/less/**/*.less', ['less']);
});

gulp.task('build', ['less']);

gulp.task('default', ['build', 'watch']);
