module.exports = function(grunt) {

    grunt.initConfig({
        riot: {
            options:{
                //template : 'jade',
                //type : 'coffeescript'
                concat: true
            },
            dist: {
                expand: false,
                src: 'tags/*.tag',
                dest: 'resources/dist/intranda_step_image-size-validation/js/tags.js',
            }
        },
        watcher: {
            scripts: {
                files: ['tags/*.tag'],
                tasks: ['riot'],
                options: {
                    spawn: false,
                },
            },
        }
    });

    grunt.loadNpmTasks('grunt-riot');
    grunt.loadNpmTasks('grunt-watcher');
    grunt.loadNpmTasks('grunt-contrib-copy');

    grunt.registerTask('default', ['riot']);
    grunt.registerTask('develop', ['riot', 'watcher']);
};
