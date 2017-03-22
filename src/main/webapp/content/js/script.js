$(document).ready(function(){

    $(window).load(function() {
        $('#status').fadeOut();
        $('#preloader').delay(350).fadeOut('slow');
    });

	var header_height = $('#header').height();

	/* parallax header */
	function parallax(){
	  var scrolled = $(window).scrollTop();
	  $('#header .backstretch img').css('top',''+-(scrolled*0.6)+'px');
      $('.heading').css('background-position', 'center '+-(scrolled*0.5)+'px');
	}


    /* backstretch slider */
    $('.header-slide').backstretch([
      "slide/bg01.jpg",
      "slide/bg02.jpg",
      "slide/bg03.jpg"
      ], {
        fade: 850,
        duration: 4000
    });


    /* navbar */
	$(window).scroll(function(){
		parallax();
		if($(window).scrollTop() > header_height){
            //$('.navbar').css('border-bottom-color', '#37bc9b');
		}else{
            //$('.navbar').css('border-bottom-color', '#fff');
		}
	});


    /* carousel testimony */
    $('#testislider').carousel({
        interval: 6000
    });

	/* carousel partner */
	var jcarousel = $('.jcarousel');

    jcarousel
        .on('jcarousel:reload jcarousel:create', function () {
            var width = jcarousel.innerWidth();

            if(width >= 992){
            	width = width / 5;
            } else if (width >= 768) {
                width = width / 4;
            } else if (width >= 480) {
                width = width / 3;
            } else if(width >= 350){
            	width = width / 2;
            }

            jcarousel.jcarousel('items').css('width', width + 'px');
        })
        .jcarousel({
            wrap: 'circular'
        });

    $('.jcarousel-control-prev')
        .jcarouselControl({
            target: '-=1'
        });

    $('.jcarousel-control-next')
        .jcarouselControl({
            target: '+=1'
    	});

    $('.jcarousel-pagination')
        .on('jcarouselpagination:active', 'a', function() {
            $(this).addClass('active');
        })
        .on('jcarouselpagination:inactive', 'a', function() {
            $(this).removeClass('active');
        })
        .on('click', function(e) {
            e.preventDefault();
        })
        .jcarouselPagination({
            perPage: 1,
            item: function(page) {
                return '<a href="#' + page + '">' + page + '</a>';
        }
    });


    /* scrolltop */
    $('.scroltop').on('click', function(event) {
        var $anchor = $(this);
        $('html, body').stop().animate({
            scrollTop: $($anchor.attr('href')).offset().top
        }, 1500, 'easeInOutExpo');
        event.preventDefault();
    });


    /* masonry layout */
    var $container = $('.container-realestate');
    $container.imagesLoaded( function(){
        $container.masonry();
    });


    /* modal */
    $('.modal').on('shown.bs.modal', function () {
        var curModal = this;
        $('.modal').each(function(){
            if(this != curModal){
                $(this).modal('hide');
            }
        });
    });


    /* tooltip */
    $('[rel="tooltip"]').tooltip();



    /* carousel single */
    $('#slider-property').carousel({
        interval: 6500
    });


    /* map property */
    $('a[href="#location"]').on('shown.bs.tab', function(){
        $("#map-property").gmap3({
            map: {
                options: {
                  center: [-7.866315,110.389574],
                  zoom: 13,
                  scrollwheel: false
                }
             },
            marker:{
                latLng: [-7.866315,110.389574],
                options: {
                 icon: new google.maps.MarkerImage(
                   "https://dl.dropboxusercontent.com/u/29545616/Preview/location.png",
                   new google.maps.Size(48, 48, "px", "px")
                 )
                }
             }
        });
    });
});

