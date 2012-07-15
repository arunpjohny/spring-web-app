$(function() {
	$.Class("app.security.login.Main", {}, {
		init : function(el, options) {
			this.el = $(el);
			this.options = $.extend({}, options);

			this.elForm = $("form", this.el);
			this.elForm.validate({
						rules : {
							j_username : {
								required : true
							},
							j_password : {
								required : true
							}
						}
					});

			this.elForm.on("submit", this.proxy("onSubmit"));
			$("input:eq(0)", this.elForm).focus();
		},

		onSubmit : function(e) {
			e.preventDefault();
			e.stopPropagation();

			if (this.elForm.valid()) {
				$.ajax({
					type : "POST",
					url : ZtUtils.getContextPath() + "/j_spring_security_check",
					data : this.elForm.serialize()
				}).done(this.proxy("onSubmitSuccess"));
			}
		},

		onSubmitSuccess : function(result,status,xhr) {
			var loc = xhr.getResponseHeader("Location");
			window.location = loc || ZtUtils.getContextPath() + "/";
		}
	});
});