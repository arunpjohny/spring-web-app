$(function() {

			$.Class("app.main.home.Main", {}, {
						init : function(el, options) {
							this.el = $(el);
							this.options = $.extend({}, options);
						}
					});
		});