AppUtils = {
	id : 0,

	getId : function() {
		return "app-jq-" + (AppUtils.id++);
	},

	getContextPath : function() {
		return _contextPath;
	}
};

$(function() {
			$("body").on("app-page-load", function(e, page) {
						if (_disable_ajax_loading) {
							window.location = AppUtils.getContextPath() + path;
						} else {
							$.bbq.pushState("#id=" + page);
						}
					});

			if (_disable_ajax_loading) {
				return;
			}

			var $navbar = $("#nav-bar");
			var $container = $("#page-container");

			var id = undefined;

			var fnLoadPage = function(url, callback) {
				$container.load(url, false, function() {
							var $a = $(".app-navigation li > a[href='" + url
									+ "']");
							var $li = $a.parent("li");
							$li.closest("ul.nav").children("li")
									.removeClass("active");
							$li.addClass("active");

							var el = $.bbq.getState("el");
							if (el) {
								$(el).ScrollTo();
							}
						});
			};

			var fnClickHandler = function(e) {
				e.preventDefault();
				e.stopPropagation();

				var $target = $(e.target);
				var href = $target.attr("href");
				if (href) {
					$.bbq.pushState("#id="
							+ href.substring(AppUtils.getContextPath().length));
				}
			};

			$(
					"ul.nav > li > a[href='"
							+ (AppUtils.getContextPath() + _pageId) + "']",
					$navbar).parent().addClass("active");

			$(".app-navigation a.nav-ajax").on("click", fnClickHandler);

			$(window).on("hashchange", function(e) {
						var href = $.bbq.getState("id");
						if (!href && !id) {
							if (_pageId && _pageId != "/") {
								id = _pageId;
								$.bbq.pushState("#id=" + (_pageId));
							} else {
								id = _pageId;
								$.bbq.pushState("#id=/home");
							}
							return;
						}

						if (!href) {
							$.bbq.pushState("id=" + id);
							return;
						}

						if (id != href) {
							fnLoadPage(AppUtils.getContextPath() + href);
							id = href;
						} else {
							var el = $.bbq.getState("el");
							if (el) {
								$(el).ScrollTo();
							}
						}
					});

			$(window).trigger("hashchange");
		});

$(function() {
			if ($.validator) {
				$.validator.setDefaults({
							onkeyup : false,
							errorClass : 'error',
							validClass : 'valid',
							errorPlacement : function(error, element) {
								var $el = $(element);
								var $parent = $el.parent();
								var $error = $(error).addClass($el
										.attr("error-placement") == "inline"
										? "help-inline"
										: "help-block");

								var fn = $el.data("appFnErrorPlacement");
								if ($.isFunction(fn)) {
									fn.apply(this, arguments);
								} else if ($el.hasClass("cb-value")
										&& $parent.hasClass("app-combo")) {
									$error.insertAfter($parent);
								} else {
									$error.insertAfter($el);
								}
							},
							highlight : function(element, errorClass,
									validClass) {
								var ctrlGroup = $(element)
										.parents(".control-group");
								if (!ctrlGroup.hasClass(errorClass)) {
									ctrlGroup.addClass(errorClass)
											.removeClass(validClass);
								}
							},
							unhighlight : function(element, errorClass,
									validClass) {
								$(element).parents(".control-group")
										.removeClass(errorClass)
										.addClass(validClass);

							}

						});

				$.validator.addMethod("app-value-list", function(value,
								element, param) {
							return !$.isArray(param) || param.length <= 0
									|| this.optional(element)
									|| param.contains(value);
						});

				$.validator.addMethod("all-true", function(value, element,
								param) {
							return true;
						});

			}
		});

(function($) {

	$.widget("ui.appcombobox", {
		_create : function() {
			if (!this.element.hasClass("app-combo")) {
				this.element.addClass("app-combo");
			}

			var html = "<input type='text' class='cb-autocomplete "
					+ (this.element.data("combosize") ? this.element
							.data("combosize") : "")
					+ "' "
					+ (this.options.placeholder ? "placeholder='"
							+ this.options.placeholder + "'" : "") + "/>"
					+ "<i class='queryall icon icon-chevron-down' ></i>"
					+ "<input class='cb-value' type='hidden' name='"
					+ (this.options.name || AppUtils.getId())
					+ "' error-placement='"
					+ (this.element.attr("error-placement") || "inline")
					+ "' all-true='true' />";
			$(this.element).html(html);

			$("i.queryall", this.element).on("click",
					$.proxy(this._onQueryAll, this));

			this.elAutoComplete = $(".cb-autocomplete", this.element);
			this.elHidden = $(".cb-value", this.element);
			this.selectedValue = 0;

			this.elAutoComplete[this.options.autocomplete]({
						search : $.proxy(this._onAutoCompleteSearch, this),
						select : $.proxy(this._onAutoCompleteSelect, this)
					}).on("blur", $.proxy(this._onAutoCompleteBlur, this));

			this.elAutoComplete.data(this.options.autocomplete)._renderItem = $
					.proxy(this._renderItem, this);

			this.getResponseFn = $.proxy(function() {
						return this.elAutoComplete
								.data(this.options.autocomplete)._response()
					}, this);
			this._initSource();

			if (this.options.value) {
				this.setValue(this.options.value);
			}
		},

		requestIndex : 0,

		_renderItem : function(ul, item) {
			return $("<li></li>").data("item.autocomplete", item)
					.append($("<a></a>").html(item.listView || item.label))
					.appendTo(ul);
		},

		_initSource : function() {
			var self = this, array, url;
			if ($.isArray(this.options.source)) {
				array = [];

				$.each(this.options.source, function(i, v) {
							array.push(self._getRecord(v));
						});

				this.source = function(request, response) {
					if ($.isFunction(this.options.filter)) {
						var a = [];
						$.each(array, $.proxy(function(i, v) {
											if (this.options.filter(v.obj)) {
												a.push(v);
											}
										}, this));
						response($.ui.autocomplete.filter(a, request.term));
					} else {
						response($.ui.autocomplete.filter(array, request.term));
					}
				};
			} else if (typeof this.options.source === "string") {
				url = this.options.source;
				this.source = function(request, response) {
					if (self.xhr) {
						self.xhr.abort();
					}

					var data = {
						query : request.term
					};

					this._trigger("beforeremotequery", null, [{
										data : data
									}]);

					self.xhr = $.ajax({
								url : AppUtils.getContextPath() + url,
								data : data
							});
					self.xhr.autocompleteRequest = ++self.requestIndex;
					self.xhr.done($.proxy(self._onRemoteSearchSuccess, self))
							.fail($.proxy(self._onRemoteSearchError, self));
				};
			} else {
				this.source = this.options.source;
			}
		},

		_getRecord : function(data) {
			var record;
			if (data) {
				var id = $.isFunction(this.options.valueField) ? this.options
						.valueField(data) : data[this.options.valueField];
				var value = $.isFunction(this.options.displayField)
						? this.options.displayField(data)
						: data[this.options.displayField];
				var list = $.isFunction(this.options.listView) ? this.options
						.listView(data) : undefined;
				record = {
					id : id,
					label : value,
					value : value,
					listView : list,
					obj : data
				};
			}
			return record;
		},

		_onAutoCompleteBlur : function() {
			if (this.elAutoComplete.val()) {
				this.elAutoComplete.val(this.selectedDescription);
			} else {
				this.elAutoComplete.val("");
			}
		},

		_onAutoCompleteSearch : function() {
			this.source({
						term : this.elAutoComplete.val()
					}, this.getResponseFn());

			return false;
		},

		_onRemoteSearchSuccess : function(data, status, jqXHR) {
			if (jqXHR.autocompleteRequest === this.requestIndex) {
				var array = [];
				var self = this;
				$.each(data[this.options.root], function(i, v) {
							array.push(self._getRecord(v));
						});

				this.getResponseFn()(array);
			}
		},

		_onRemoteSearchError : function(jqXHR, textStatus, errorThrown) {
			if (jqXHR.autocompleteRequest === this.requestIndex) {
				this.getResponseFn()([]);
			}
		},

		_onAutoCompleteSelect : function(e, data) {
			this._setValue(data.item);
		},

		_fireSelectedEvent : function(data) {
			if (data) {
				this._trigger("select", 0, data.obj);
			} else {
				this._trigger("select");
			}
		},

		_onQueryAll : function() {
			if (this.elAutoComplete[this.options.autocomplete]("widget")
					.is(":visible")) {
				this.elAutoComplete[this.options.autocomplete]("close");
				return;
			}

			// To nullify the blur event's closing timout
			var closing = this.elAutoComplete.data(this.options.autocomplete).closing;
			if (closing) {
				clearTimeout(closing);
			}

			this.source({
						term : ""
					}, this.getResponseFn());
			this.elAutoComplete.focus();
		},

		_setValue : function(data, isSetValue) {
			if (data) {
				this.elHidden.val(data.id);
			} else {
				this.elHidden.val("");
			}

			this.selectedValue = data ? data.id : 0;
			this.selectedDescription = data ? data.value : "";

			if (!isSetValue
					|| (isSetValue && !this.options.ignoreSelectEventOnSetValue)) {
				this._fireSelectedEvent(data);
			}
		},

		getValue : function() {
			return this.selectedValue;
		},

		setValue : function(data) {
			if (data) {
				var record = this._getRecord(data);
				this._setValue(record, true);
				this.elAutoComplete.val(record.value);
			} else {
				this._setValue(false, true);
				this.elAutoComplete.val("");
			}
		},

		options : {
			autocomplete : "autocomplete",
			valueField : "id",
			displayField : "description",
			root : "data",
			ignoreSelectEventOnSetValue : false
		}
	});

})(jQuery);

$(function() {

	var fnWrapMessage = function(message) {
		return "<li>" + message + "</li>";
	};
	var fnSetCookieMessage = function(message) {
		$.cookie("ajax.page-message", message, {
					path : AppUtils.getContextPath()
				});
	};
	var fnGetMessages = function(message) {
		var messages = [];
		if (message) {
			if ($.isArray(message)) {
				$.each(message, function(i, v) {
							var m;
							if ($.isArray(v)) {
								m = v.join("<br />");
							} else {
								m = v;
							}
							messages.push(fnWrapMessage(m));
						});
			} else {
				messages.push(fnWrapMessage(message));
			}
		}
		return messages;
	};

	var hideCallId = false;
	var elAjaxMessage = $("#ajax-messages");
	var elAjaxMessageAlert = $(".alert", elAjaxMessage);
	var elAjaxMessageContent = $(".message-content", elAjaxMessage);
	var alertClass = {
		"success" : "alert-success",
		"error" : "alert-error",
		"warning" : "alert-block"
	};

	var fnHide = function() {
		elAjaxMessage.hide();
	};
	var fnSetHide = function(timeout) {
		if (hideCallId) {
			fnClearHide(hideCallId);
		}
		hideCallId = setTimeout(fnHide, timeout);
	};
	var fnClearHide = function() {
		clearTimeout(hideCallId);
		hideCallId = false;
	};
	var fnWrapMessageElements = function(message) {
		if ($.isArray(message)) {
			message = message.join("");
		}
		return "<ul class='unstyled' style='margin-bottom: 0'>" + message
				+ "</ul>";
	};
	var fnShowMessageEl = function(message, type, el) {
		$(".message-content", el).html(fnWrapMessageElements(message));
		$.each(alertClass, function(i, v) {
					el.removeClass(v);
				});
		el.addClass(alertClass[type]);
		el.show();
		el.effect("highlight", {}, 3000);
	};
	var fnShowMessage = function(message, type, el) {
		if (el) {
			fnShowMessageEl(message, type, el);
			return;
		}

		if (hideCallId) {
			fnClearHide(hideCallId);
		}
		elAjaxMessageContent.html(fnWrapMessageElements(message));
		$.each(alertClass, function(i, v) {
					elAjaxMessageAlert.removeClass(v);
				});
		elAjaxMessageAlert.addClass(alertClass[type]);
		elAjaxMessage.show();
		elAjaxMessageAlert.effect("highlight", {}, 3000);
	};
	var fnAdjustView = function() {
		elAjaxMessage.css("right", ((($(document).width() - $(".container")
						.width()) / 2) + 5)
						+ "px");
	};
	var fnProcessJsonResponse = function(xhr, $form, opts) {
		opts = opts || {};

		if ($form) {
			$form.valid();
		}

		var resp = undefined;
		var messages = undefined;
		if (xhr.responseText) {
			resp = $.parseJSON(xhr.responseText);
			messages = resp ? fnGetMessages(resp["_page-message"]) : [];
		}

		if (resp && resp["_app-redirect-path"]) {
			if ($.isArray(messages) && messages.length > 0) {
				fnSetCookieMessage(messages.join(""));
			}
			var path = resp["_app-redirect-path"];
			$("body").trigger("app-page-load", path);
		} else {
			var fieldErrors = resp ? resp["_field-errors"] : undefined;
			if (fieldErrors) {
				var fields = opts["fields"] || {};

				var fieldErrorMessages = {};
				$.each(fieldErrors, function(name) {
							var field;
							if (fields[name]) {
								field = fields[name];
							} else {
								field = name;
							}
							if ($form
									&& $form.validate().elements()
											.is("input[name='" + field + "']")) {
								var fmsg = fieldErrors[name];
								if ($.isArray(fmsg)) {
									fieldErrorMessages[field] = fmsg
											.join("<br />");
								} else {
									fieldErrorMessages[field] = fmsg;
								}
							} else {
								messages.push(fnWrapMessage(fieldErrors[name]));
							}
						});

				if ($form) {
					var fn = function() {
						$form.validate().showErrors(fieldErrorMessages);
					};
					setTimeout(fn, 500);
				}
			}
			if ($.isArray(messages) && messages.length > 0) {
				var msgtype = resp["_page-message-type"] || opts.type
						|| "success";
				var msgel = opts.el;
				fnShowMessage(messages, msgtype, msgel);
				if (msgtype == "success" && !msgel) {
					fnSetHide(5000);
				}
			} else if (opts.hideMessage) {
				fnHide();
				fnClearHide();
			}
		}
	};

	$(window).on("resize", fnAdjustView);
	fnAdjustView();

	if (_error_message) {
		fnShowMessage(_error_message, "error");
	} else {
		var m = $.cookie("v2.ajax.page-message");
		if (m) {
			fnShowMessage(m, "success");
			fnSetCookieMessage("");
		}
	}

	$("a.close", elAjaxMessage).on("click", function() {
				fnHide();
				fnClearHide();
			});

	$.extend(AppUtils, {
				processAjaxSuccess : function(xhr, $form, opts) {
					try {
						fnProcessJsonResponse(xhr, $form, opts);
					} catch (err) {
						// Ignore
					}
				},

				processAjaxError : function(xhr, $form, opts) {
					try {
						fnProcessJsonResponse(xhr, $form, opts);
					} catch (err) {
						fnShowMessage(
								"Unknown error occured while processing the request!",
								"error");
					}
				},

				successMsg : function(message, millis) {
					fnShowMessage(message, "success");
					if (millis > 0) {
						fnSetHide(millis);
					}
				},

				warningMsg : function(message, millis) {
					fnShowMessage(message, "warning");
					if (millis > 0) {
						fnSetHide(millis);
					}
				},

				errorMsg : function(message, millis) {
					fnShowMessage(message, "error");
					if (millis > 0) {
						fnSetHide(millis);
					}
				}
			});

	var msgprocessing = {
		error : true,
		success : true,
		hideMessage : true
	};
	var fnGetMsgProcessingOpts = function(options) {
		return $.extend({}, msgprocessing, options.msgprocessing || {});
	};

	elAjaxMessage.ajaxSuccess(function(e, xhr, options) {
				var proc = fnGetMsgProcessingOpts(options);
				if (xhr.responseText && proc.success
						&& options.dataType == "json") {
					var val = options.validator || {};
					AppUtils.processAjaxSuccess(xhr, val.form, proc);
				}
			});

	elAjaxMessage.ajaxError(function(e, jqXHR, settings, thrownError) {
		var proc = fnGetMsgProcessingOpts(settings);
		if (proc.error) {
			if (jqXHR.status == 403) {
				fnShowMessage("Unable to access the specified resource.",
						"error");
			} else if (jqXHR.status == 404) {
				fnShowMessage("Unable to find the specified resource.", "error");
			} else if (jqXHR.responseText) {
				var val = settings.validator || {};
				AppUtils.processAjaxError(jqXHR, val.form
								? $(val.form)
								: undefined, {
							fields : val.fields
						});
			}
		}
	});

	$.ajaxSetup({
				timeout : 600000,
				dataType : "json",
				cache : false,
				headers : {
					"Accept" : "application/json, text/javascript"
				},
				msgprocessing : msgprocessing
			});

});

$(function() {
			if ($.views) {
				$.views.helpers({
							timeInMillis : function() {
								return new Date().getTime();
							}
						});
			}
		});

(function($) {

	$.widget("ui.apppager", {
				_create : function() {
					if (!this.element.hasClass("pagination")) {
						this.element.addClass("pagination");
					}
					if (!this.element.hasClass("pagination-centered")) {
						this.element.addClass("pagination-centered");
					}

					this.list = $("<ul></ul>").appendTo(this.element.empty());

					this.element.on("click", "li > a", $.proxy(
									this._onPageClick, this));

					this._updatePager(this.options.totalPages || 0,
							this.options.currentPage || 0);
				},

				_onPageClick : function(e) {
					var $target = $(e.currentTarget);
					var page = $target.data("page");
					if (page) {
						this._trigger("pageselect", 0, page);
						$("li.active", this.list).removeClass("active");
						$target.parent("li").addClass("active");
					}
				},

				_updatePager : function(totalPages, currentPage) {
					var pages = [];
					if (totalPages < 10) {
						for (var i = 1; i <= totalPages; i++) {
							pages.push(this
									._getPagerElementHtml(i, currentPage));
						}
					} else {
						var added = {};

						for (var i = 1; i <= 2; i++) {
							pages.push(this
									._getPagerElementHtml(i, currentPage));
							added[i] = true;
						}

						if (currentPage - 4 > 3) {
							pages.push("<li class='space disabled'>...</li>");
						}

						var varPartStart = currentPage < 5 ? 3 : currentPage
								- 4;
						var varPartEnd = currentPage < 5 ? 9 : currentPage + 4;
						for (var i = varPartStart; i <= varPartEnd; i++) {
							if (i > 0 && !added[i]) {
								pages.push(this._getPagerElementHtml(i,
										currentPage));
								added[i] = true;
							}
						}

						if (currentPage + 4 < totalPages - 2) {
							pages.push("<li class='space disabled'>...</li>");
						}

						for (var i = totalPages - 1; i <= totalPages; i++) {
							if (!added[i]) {
								pages.push(this._getPagerElementHtml(i,
										currentPage));
								added[i] = true;
							}
						}
					}
					this.list.empty().html(pages.join(""));
				},

				_getPagerElementHtml : function(page, currentPage) {
					return "<li class=" + (page == currentPage ? "active" : "")
							+ "><a data-page='" + page + "'>" + page
							+ "</a></li>";
				},

				update : function(totalPages, currentPage) {
					this._updatePager(totalPages, currentPage);
				},

				options : {}
			});

})(jQuery);