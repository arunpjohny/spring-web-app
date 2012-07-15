$(function() {

	$.Class("app.main.contactus.Main", {}, {
				init : function(el, options) {
					this.el = $(el);
					this.options = $.extend({}, options);

					this.$form = $("form", this.el);

					this.$formValidator = this.$form.validate({
								rules : {
									name : {
										required : true
									},
									mobile : {
										required : true,
										digits : true,
										minlength : 10
									},
									from : {
										required : true,
										email : true
									},
									subject : {
										required : true
									},
									body : {
										required : true
									}
								}
							});

					$(".send-mail", this.$form).on("click",
							this.proxy("onSendMailClcik"));
					$(".cancel-mail", this.$form).on("click",
							this.proxy("onCancelMailClcik"));
				},

				onSendMailClcik : function(e) {
					if (!this.$form.valid() || this._mailing_in_progress) {
						return;
					}

					$(".form-actions .btn", this.$form).addClass("disabled");
					this._mailing_in_progress = true;
					$.ajax({
								url : AppUtils.getContextPath()
										+ "/contactus/mail",
								data : this.$form.serialize(),
								type : "POST"
							}).done(this.proxy("onSendMailSuccess"))
							.always(this.proxy("onSendMailComplete"));
				},

				onSendMailSuccess : function() {
					this.$form.resetForm();
					this.$formValidator.resetForm();
				},

				onSendMailComplete : function() {
					$(".form-actions .btn", this.$form).removeClass("disabled");
					this._mailing_in_progress = false;
				},

				onCancelMailClcik : function(e) {
					this.$form.resetForm();
					this.$formValidator.resetForm();
				}
			});
});