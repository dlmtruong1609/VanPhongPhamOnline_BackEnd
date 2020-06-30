<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--
=========================================================
Material Dashboard - v2.1.2
=========================================================

Product Page: https://www.creative-tim.com/product/material-dashboard
Copyright 2020 Creative Tim (https://www.creative-tim.com)
Coded by Creative Tim

=========================================================
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software. -->
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<link rel="apple-touch-icon" sizes="76x76"
	href="/assets/img/apple-icon.png">
<link rel="icon" type="image/png" href="/assets/img/favicon.png">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>ANANAS</title>
<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no'
	name='viewport' />
<!--     Fonts and icons     -->
<link rel="stylesheet" type="text/css"
	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
<!-- CSS Files -->
<link href="/assets/css/material-dashboard.css?v=2.1.2" rel="stylesheet" />
<!-- CSS Just for demo purpose, don't include it in your project -->
<link href="/assets/demo/demo.css" rel="stylesheet" />
<style type="text/css">
.btn-custom {
	float: right;
	position: absolute;
	right: 10%;
}

form.navbar-form {
	margin-right: 10%;
}
</style>
</head>

<body class="">
	<div class="wrapper ">
		<div class="sidebar" data-color="purple" data-background-color="white"
			data-image="/assets/img/sidebar-1.jpg">
			<!--
        Tip 1: You can change the color of the sidebar using: data-color="purple | azure | green | orange | danger"

        Tip 2: you can also add an image using data-image tag
    -->
			<div class="logo">
				<a href="http://www.creative-tim.com"
					class="simple-text logo-normal">Ananas Admin</a>
			</div>
			<div class="sidebar-wrapper">
				<ul class="nav">
					<li class="nav-item  "><a class="nav-link"
						href="./dashboard.html"> <i class="material-icons">dashboard</i>
							<p>Dashboard</p>
					</a></li>
					<li class="nav-item active "><a class="nav-link"
						href="/admin/product?index=0"> <i class="material-icons">content_paste</i>
							<p>Quản lý sản phẩm</p>
					</a></li>
					<li class="nav-item "><a class="nav-link"
						href="/admin/customer?index=0"> <i class="material-icons">person</i>
							<p>Quản lý khách hàng</p>
					</a></li>
					<li class="nav-item "><a class="nav-link"
						href="/admin/order?index=0"> <i class="material-icons">library_books</i>
							<p>Quản lý đơn hàng</p>
					</a></li>
					<li class="nav-item "><a class="nav-link"
						href="/admin/category?index=0"> <i class="fa fa-sort"
							aria-hidden="true"></i>
							<p>Quản lý loại sản phẩm</p>
					</a></li>
					<li class="nav-item "><a class="nav-link"
						href="/admin/supplier?index=0"> <i class="fa fa-university"
							aria-hidden="true"></i>
							<p>Quản lý nhà cung cấp</p>
					</a></li>
				</ul>
			</div>
		</div>
		<div class="main-panel">
			<!-- Navbar -->
			<nav
				class="navbar navbar-expand-lg navbar-transparent navbar-absolute fixed-top ">
				<div class="container-fluid">
					<div class="navbar-wrapper">
						<a class="navbar-brand" href="javascript:;">Thông tin sản phẩm</a>
					</div>
					<button class="navbar-toggler" type="button" data-toggle="collapse"
						aria-controls="navigation-index" aria-expanded="false"
						aria-label="Toggle navigation">
						<span class="sr-only">Toggle navigation</span> <span
							class="navbar-toggler-icon icon-bar"></span> <span
							class="navbar-toggler-icon icon-bar"></span> <span
							class="navbar-toggler-icon icon-bar"></span>
					</button>
					<div class="collapse navbar-collapse justify-content-end">
						<form class="navbar-form" action="/api/v1/admin/product/search"
							method="get">
							<div class="input-group no-border">
								<input type="text" name="keyword" value="" class="form-control"
									placeholder="Search...">
								<button type="submit"
									class="btn btn-white btn-round btn-just-icon">
									<i class="material-icons">search</i>
									<div class="ripple-container"></div>
								</button>
							</div>
						</form>
					</div>
				</div>
			</nav>
			<!-- End Navbar -->
			<div class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="text-center">
							<div class="alert alert-success" <c:if test="${param.message == null}">hidden</c:if> role="alert">
							  <%= request.getParameter("message") %>
							</div>
						</div>
						<div class="col-md-12">
							<div class="card">
								<div class="card-header card-header-primary">
									<h4 class="card-title ">Quản lý sản phẩm</h4>
									<p class="card-category">Thêm, xoá, sửa, tìm kiếm tại đây</p>
									<button class="btn-custom btn btn-warning" style="top: 10px"
										data-toggle="modal" data-target="#addModal">
										<span class="h4 pr-2">+</span> Thêm sản phẩm
									</button>
								</div>
								<form class="modal fade bd-example-modal-lg" id="addModal"
									action="/admin/product/add" method="post" tabindex="-1"
									role="dialog" aria-labelledby="exampleModalLabel"
									aria-hidden="true" modelAttribute="product">
									<div class="modal-dialog modal-lg" role="document">
										<div class="modal-content">
											<div class="modal-header">
												<h5 class="modal-title" id="exampleModalLabel">Modal
													title</h5>
												<button type="button" class="close" data-dismiss="modal"
													aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
											</div>
											<div class="modal-body">
												<div class="md-form form">
													<div class="input-group mb-3">
														<div class="input-group-prepend">
															<label class="input-group-text" for="inputGroupSelect01">Chọn
																loại sản phẩm</label>
														</div>
														<select name="category" class="custom-select"
															id="inputGroupSelect01">
															<c:forEach var="c" items="${categories }">
																<option value="${c.id }" selected>${c.name }</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="row mt-3">
													<div class="col-lg-4">
														<p>
															<strong>Tên sản phẩm</strong>
														</p>
														<input type="text" class="md-text form-control"
															name="name" placeholder="Nhập tên sản phẩm">
													</div>
													<div class="col-lg-4">
														<p>
															<strong>Số Lượng</strong>
														</p>
														<div class="md-form form">
															<input type="text" name="inventory" required=""
																class="md-text form-control"
																placeholder="Nhập số lượng sản phẩm">
														</div>
													</div>
													<div class="col-lg-4">
														<p>
															<strong>Giá</strong>
														</p>
														<div class="md-form form">
															<input ype="text" name="price" required=""
																class="md-text form-control"
																placeholder="Nhập giá sản phẩm">
														</div>
													</div>
												</div>
												<div class="mt-3 ">
													<p>
														<strong>Mô tả sản phẩm</strong>
													</p>
													<div class="md-form form">
														<textarea name="description" class="md-text form-control"
															placeholder="Nhập mô tả sản phẩm"></textarea>
													</div>
												</div>
												<div class="row">
													<div class="mt-3 col-sm-6 col-lg-6 col-md-6">
														<p>
															<strong>Nhà cung cấp</strong>
														</p>
														<div class="md-form form">
															<select name="supplier" class="md-text form-control">
																<c:forEach var="supplier" items="${suppliers }">
																	<option selected value="${supplier.id }">${supplier.name }</option>
																</c:forEach>
															</select>
														</div>
													</div>
													<div class="mt-3 row col-sm-6 col-lg-6 col-md-6">
														<div class="col-lg-4">
															<p>
																<strong>Hình Ảnh</strong>
															</p>
															<div class="md-form form">
																<input class="md-text form-control" id="fileUploader"
																	type="file" name="fileUploader" accept="image/*"
																	style="display: none;"><label
																	class="form-control" for="fileUploader"
																	style="margin-top: 21px;">Chọn</label>
															</div>
														</div>
														<div class="col-lg-6">
															<img style="width: 200px; height: 200px;">
														</div>
													</div>
												</div>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-secondary"
													data-dismiss="modal">Đóng</button>
												<button type="submit" class="btn btn-primary">Thêm</button>
											</div>
										</div>
									</div>
								</form>
								<div class="card-body">
									<div class="table-responsive">
										<table class="table">
											<thead class=" text-primary">
												<th>STT</th>
												<th>Tên</th>
												<th>Đơn giá</th>
												<th>Chi tiết</th>
												<th>Cập nhật</th>
											</thead>
											<tbody>
												<c:forEach var="p" items="${listProduct }" varStatus="loop">
													<tr>
														<td>${loop.index }</td>
														<td>${p.name }</td>
														<td>${p.price }</td>
														<td>
															<a href="" data-toggle="modal"
																data-target="#detailModal${p.id }">Xem chi tiết</a>
															<form class="modal fade bd-example-modal-lg"
															id="detailModal${p.id }"tabindex="-1" role="dialog"
															aria-labelledby="exampleModalLabel" aria-hidden="true"
															modelAttribute="product">
															<div class=" modal-dialog modal-lg" role="document">
																<div class="modal-content">
																	<div class="modal-header">
																		<h5 class="modal-title" id="exampleModalLabel">Cập nhật sản phẩm</h5>
																		<button type="button" class="close"
																			data-dismiss="modal" aria-label="Close">
																			<span aria-hidden="true">&times;</span>
																		</button>
																	</div>
																	<div class="modal-body">
																		<div class="md-form form">
																			<div class="input-group mb-3">
																				<div class="input-group-prepend">
																					<label class="input-group-text"
																						for="inputGroupSelect01">Chọn loại sản
																						phẩm</label>
																				</div>
																				<select disabled name="category" class="custom-select"
																					id="inputGroupSelect01${p.id }">
																					<c:forEach var="c" items="${categories }">
																						<option value="${c.id }" ${c.id == p.category.id ? 'selected' : '' }>${c.name}</option>
																					</c:forEach>
																				</select>
																			</div>
																		</div>
																		<div class="row mt-3">
																			<div class="col-lg-4">
																				<p>
																					<strong>Tên sản phẩm</strong>
																				</p>
																				<input type="hidden" name="id" value="${p.id }"/>
																				<input type="text" class="md-text form-control"
																					name="name" disabled value="${p.name }" placeholder="Nhập tên sản phẩm">
																			</div>
																			<div class="col-lg-4">
																				<p>
																					<strong>Số Lượng</strong>
																				</p>
																				<div class="md-form form">
																					<input type="text" value="${p.inventory }" name="inventory" required=""
																						class="md-text form-control"
																						placeholder="Nhập số lượng sản phẩm" disabled>
																				</div>
																			</div>
																			<div class="col-lg-4">
																				<p>
																					<strong>Giá</strong>
																				</p>
																					<input type="text" value="${p.price }" name="price" required=""
																						class="md-text form-control"
																						placeholder="Nhập giá sản phẩm" disabled>
																			</div>
																		</div>
																		<div class="mt-3 ">
																			<p>
																				<strong>Mô tả sản phẩm</strong>
																			</p>
																			<div class="md-form form">
																				<textarea name="description"
																					class="md-text form-control"
																					placeholder="Nhập mô tả sản phẩm" disabled>${p.description }</textarea>
																			</div>
																		</div>
																		<div class="row">
																			<div class="mt-3 col-sm-6 col-lg-6 col-md-6">
																				<p>
																					<strong>Nhà cung cấp</strong>
																				</p>
																				<div class="md-form form">
																					<select name="supplier" disabled
																						class="md-text form-control">
																						<c:forEach var="supplier" items="${suppliers }">
																							<option ${supplier.id == p.supplier.id ? 'selected' : '' } value="${supplier.id }">${supplier.name }</option>
																						</c:forEach>
																					</select>
																				</div>
																			</div>
																			<div class="mt-3 row col-sm-6 col-lg-6 col-md-6">
																				<div class="col-lg-4">
																					<p>
																						<strong>Hình Ảnh</strong>
																					</p>
																				</div>
																				<div class="col-lg-6">
																					<img src="${p.urlImage}" style="width: 200px; height: 200px;">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="modal-footer">
																		<button type="button" class="btn btn-secondary"
																			data-dismiss="modal">Đóng</button>
																	</div>
																</div>
															</div>
														</form>
														</td>
														<td class="text-primary form-inline">
															<button class="btn btn-primary" data-toggle="modal"
																data-target="#updateModal${p.id }">Cập nhật</button>
																<form class="modal fade bd-example-modal-lg"
															id="updateModal${p.id }" action="/admin/product/update"
															method="post" tabindex="-1" role="dialog"
															aria-labelledby="exampleModalLabel" aria-hidden="true"
															modelAttribute="product">
															<div class=" modal-dialog modal-lg" role="document">
																<div class="modal-content">
																	<div class="modal-header">
																		<h5 class="modal-title" id="exampleModalLabel">Cập nhật sản phẩm</h5>
																		<button type="button" class="close"
																			data-dismiss="modal" aria-label="Close">
																			<span aria-hidden="true">&times;</span>
																		</button>
																	</div>
																	<div class="modal-body">
																		<div class="md-form form">
																			<div class="input-group mb-3">
																				<div class="input-group-prepend">
																					<label class="input-group-text"
																						for="inputGroupSelect01">Chọn loại sản
																						phẩm</label>
																				</div>
																				<select name="category" class="custom-select"
																					id="inputGroupSelect01${p.id }">
																					<c:forEach var="c" items="${categories }">
																						<option value="${c.id }" ${c.id == p.category.id ? 'selected' : '' }>${c.name}</option>
																					</c:forEach>
																				</select>
																			</div>
																		</div>
																		<div class="row mt-3">
																			<div class="col-lg-4">
																				<p>
																					<strong>Tên sản phẩm</strong>
																				</p>
																				<input type="hidden" name="id" value="${p.id }"/>
																				<input type="text" class="md-text form-control"
																					name="name" value="${p.name }" placeholder="Nhập tên sản phẩm">
																			</div>
																			<div class="col-lg-4">
																				<p>
																					<strong>Số Lượng</strong>
																				</p>
																				<div class="md-form form">
																					<input type="text" value="${p.inventory }" name="inventory" required=""
																						class="md-text form-control"
																						placeholder="Nhập số lượng sản phẩm">
																				</div>
																			</div>
																			<div class="col-lg-4">
																				<p>
																					<strong>Giá</strong>
																				</p>
																					<input type="text" value="${p.price }" name="price" required=""
																						class="md-text form-control"
																						placeholder="Nhập giá sản phẩm">
																			</div>
																		</div>
																		<div class="mt-3 ">
																			<p>
																				<strong>Mô tả sản phẩm</strong>
																			</p>
																			<div class="md-form form">
																				<textarea name="description"
																					class="md-text form-control"
																					placeholder="Nhập mô tả sản phẩm">${p.description }</textarea>
																			</div>
																		</div>
																		<div class="row">
																			<div class="mt-3 col-sm-6 col-lg-6 col-md-6">
																				<p>
																					<strong>Nhà cung cấp</strong>
																				</p>
																				<div class="md-form form">
																					<select name="supplier"
																						class="md-text form-control">
																						<c:forEach var="supplier" items="${suppliers }">
																							<option ${supplier.id == p.supplier.id ? 'selected' : '' } value="${supplier.id }">${supplier.name }</option>
																						</c:forEach>
																					</select>
																				</div>
																			</div>
																			<div class="mt-3 row col-sm-6 col-lg-6 col-md-6">
																				<div class="col-lg-4">
																					<p>
																						<strong>Hình Ảnh</strong>
																					</p>
																					<div class="md-form form">
																						<input class="md-text form-control"
																							id="fileUploader" type="file" name="fileUploader"
																							accept="image/*" style="display: none;"><label
																							class="form-control" for="fileUploader"
																							style="margin-top: 21px;">Chọn</label>
																					</div>
																				</div>
																				<div class="col-lg-6">
																					<img src="${p.urlImage}" style="width: 200px; height: 200px;">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="modal-footer">
																		<button type="button" class="btn btn-secondary"
																			data-dismiss="modal">Đóng</button>
																		<button type="submit" class="btn btn-primary">Cập nhật</button>
																	</div>
																</div>
															</div>
														</form>
															<form action="/admin/product/delete" method="get">
																<input class="btn btn-danger" type="submit" value="Xoá" />
																<input type="hidden" name="id" value="${p.id }">
															</form>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--   Core JS Files   -->
				<script src="/assets/js/core/jquery.min.js"></script>
				<script src="/assets/js/core/popper.min.js"></script>
				<script src="/assets/js/core/bootstrap-material-design.min.js"></script>
				<script src="/assets/js/plugins/perfect-scrollbar.jquery.min.js"></script>
				<!-- Plugin for the momentJs  -->
				<script src="/assets/js/plugins/moment.min.js"></script>
				<!--  Plugin for Sweet Alert -->
				<script src="/assets/js/plugins/sweetalert2.js"></script>
				<!-- Forms Validations Plugin -->
				<script src="/assets/js/plugins/jquery.validate.min.js"></script>
				<!-- Plugin for the Wizard, full documentation here: https://github.com/VinceG/twitter-bootstrap-wizard -->
				<script src="/assets/js/plugins/jquery.bootstrap-wizard.js"></script>
				<!--	Plugin for Select, full documentation here: http://silviomoreto.github.io/bootstrap-select -->
				<script src="/assets/js/plugins/bootstrap-selectpicker.js"></script>
				<!--  Plugin for the DateTimePicker, full documentation here: https://eonasdan.github.io/bootstrap-datetimepicker/ -->
				<script src="/assets/js/plugins/bootstrap-datetimepicker.min.js"></script>
				<!--  DataTables.net Plugin, full documentation here: https://datatables.net/  -->
				<script src="/assets/js/plugins/jquery.dataTables.min.js"></script>
				<!--	Plugin for Tags, full documentation here: https://github.com/bootstrap-tagsinput/bootstrap-tagsinputs  -->
				<script src="/assets/js/plugins/bootstrap-tagsinput.js"></script>
				<!-- Plugin for Fileupload, full documentation here: http://www.jasny.net/bootstrap/javascript/#fileinput -->
				<script src="/assets/js/plugins/jasny-bootstrap.min.js"></script>
				<!--  Full Calendar Plugin, full documentation here: https://github.com/fullcalendar/fullcalendar    -->
				<script src="/assets/js/plugins/fullcalendar.min.js"></script>
				<!-- Vector Map plugin, full documentation here: http://jvectormap.com/documentation/ -->
				<script src="/assets/js/plugins/jquery-jvectormap.js"></script>
				<!--  Plugin for the Sliders, full documentation here: http://refreshless.com/nouislider/ -->
				<script src="/assets/js/plugins/nouislider.min.js"></script>
				<!-- Include a polyfill for ES6 Promises (optional) for IE11, UC Browser and Android browser support SweetAlert -->
				<script
					src="https://cdnjs.cloudflare.com/ajax/libs/core-js/2.4.1/core.js"></script>
				<!-- Library for adding dinamically elements -->
				<script src="/assets/js/plugins/arrive.min.js"></script>
				<!--  Google Maps Plugin    -->
				<script
					src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>
				<!-- Chartist JS -->
				<script src="/assets/js/plugins/chartist.min.js"></script>
				<!--  Notifications Plugin    -->
				<script src="/assets/js/plugins/bootstrap-notify.js"></script>
				<!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
				<script src="/assets/js/material-dashboard.js?v=2.1.2"
					type="text/javascript"></script>
				<!-- Material Dashboard DEMO methods, don't include it in your project! -->
				<script src="/assets/demo/demo.js"></script>
				<script>
					$(document)
							.ready(
									function() {
										$()
												.ready(
														function() {
															$sidebar = $('.sidebar');

															$sidebar_img_container = $sidebar
																	.find('.sidebar-background');

															$full_page = $('.full-page');

															$sidebar_responsive = $('body > .navbar-collapse');

															window_width = $(
																	window)
																	.width();

															fixed_plugin_open = $(
																	'.sidebar .sidebar-wrapper .nav li.active a p')
																	.html();

															if (window_width > 767
																	&& fixed_plugin_open == 'Dashboard') {
																if ($(
																		'.fixed-plugin .dropdown')
																		.hasClass(
																				'show-dropdown')) {
																	$(
																			'.fixed-plugin .dropdown')
																			.addClass(
																					'open');
																}

															}

															$('.fixed-plugin a')
																	.click(
																			function(
																					event) {
																				// Alex if we click on switch, stop propagation of the event, so the dropdown will not be hide, otherwise we set the  section active
																				if ($(
																						this)
																						.hasClass(
																								'switch-trigger')) {
																					if (event.stopPropagation) {
																						event
																								.stopPropagation();
																					} else if (window.event) {
																						window.event.cancelBubble = true;
																					}
																				}
																			});

															$(
																	'.fixed-plugin .active-color span')
																	.click(
																			function() {
																				$full_page_background = $('.full-page-background');

																				$(
																						this)
																						.siblings()
																						.removeClass(
																								'active');
																				$(
																						this)
																						.addClass(
																								'active');

																				var new_color = $(
																						this)
																						.data(
																								'color');

																				if ($sidebar.length != 0) {
																					$sidebar
																							.attr(
																									'data-color',
																									new_color);
																				}

																				if ($full_page.length != 0) {
																					$full_page
																							.attr(
																									'filter-color',
																									new_color);
																				}

																				if ($sidebar_responsive.length != 0) {
																					$sidebar_responsive
																							.attr(
																									'data-color',
																									new_color);
																				}
																			});

															$(
																	'.fixed-plugin .background-color .badge')
																	.click(
																			function() {
																				$(
																						this)
																						.siblings()
																						.removeClass(
																								'active');
																				$(
																						this)
																						.addClass(
																								'active');

																				var new_color = $(
																						this)
																						.data(
																								'background-color');

																				if ($sidebar.length != 0) {
																					$sidebar
																							.attr(
																									'data-background-color',
																									new_color);
																				}
																			});

															$(
																	'.fixed-plugin .img-holder')
																	.click(
																			function() {
																				$full_page_background = $('.full-page-background');

																				$(
																						this)
																						.parent(
																								'li')
																						.siblings()
																						.removeClass(
																								'active');
																				$(
																						this)
																						.parent(
																								'li')
																						.addClass(
																								'active');

																				var new_image = $(
																						this)
																						.find(
																								"img")
																						.attr(
																								'src');

																				if ($sidebar_img_container.length != 0
																						&& $('.switch-sidebar-image input:checked').length != 0) {
																					$sidebar_img_container
																							.fadeOut(
																									'fast',
																									function() {
																										$sidebar_img_container
																												.css(
																														'background-image',
																														'url("'
																																+ new_image
																																+ '")');
																										$sidebar_img_container
																												.fadeIn('fast');
																									});
																				}

																				if ($full_page_background.length != 0
																						&& $('.switch-sidebar-image input:checked').length != 0) {
																					var new_image_full_page = $(
																							'.fixed-plugin li.active .img-holder')
																							.find(
																									'img')
																							.data(
																									'src');

																					$full_page_background
																							.fadeOut(
																									'fast',
																									function() {
																										$full_page_background
																												.css(
																														'background-image',
																														'url("'
																																+ new_image_full_page
																																+ '")');
																										$full_page_background
																												.fadeIn('fast');
																									});
																				}

																				if ($('.switch-sidebar-image input:checked').length == 0) {
																					var new_image = $(
																							'.fixed-plugin li.active .img-holder')
																							.find(
																									"img")
																							.attr(
																									'src');
																					var new_image_full_page = $(
																							'.fixed-plugin li.active .img-holder')
																							.find(
																									'img')
																							.data(
																									'src');

																					$sidebar_img_container
																							.css(
																									'background-image',
																									'url("'
																											+ new_image
																											+ '")');
																					$full_page_background
																							.css(
																									'background-image',
																									'url("'
																											+ new_image_full_page
																											+ '")');
																				}

																				if ($sidebar_responsive.length != 0) {
																					$sidebar_responsive
																							.css(
																									'background-image',
																									'url("'
																											+ new_image
																											+ '")');
																				}
																			});
															$('.show').click(function () {
																$('.modal-dialog').removeClass("d-none");
															})

															$(
																	'.switch-sidebar-image input')
																	.change(
																			function() {
																				$full_page_background = $('.full-page-background');

																				$input = $(this);

																				if ($input
																						.is(':checked')) {
																					if ($sidebar_img_container.length != 0) {
																						$sidebar_img_container
																								.fadeIn('fast');
																						$sidebar
																								.attr(
																										'data-image',
																										'#');
																					}

																					if ($full_page_background.length != 0) {
																						$full_page_background
																								.fadeIn('fast');
																						$full_page
																								.attr(
																										'data-image',
																										'#');
																					}

																					background_image = true;
																				} else {
																					if ($sidebar_img_container.length != 0) {
																						$sidebar
																								.removeAttr('data-image');
																						$sidebar_img_container
																								.fadeOut('fast');
																					}

																					if ($full_page_background.length != 0) {
																						$full_page
																								.removeAttr(
																										'data-image',
																										'#');
																						$full_page_background
																								.fadeOut('fast');
																					}

																					background_image = false;
																				}
																			});

															$(
																	'.switch-sidebar-mini input')
																	.change(
																			function() {
																				$body = $('body');

																				$input = $(this);

																				if (md.misc.sidebar_mini_active == true) {
																					$(
																							'body')
																							.removeClass(
																									'sidebar-mini');
																					md.misc.sidebar_mini_active = false;

																					$(
																							'.sidebar .sidebar-wrapper, .main-panel')
																							.perfectScrollbar();

																				} else {

																					$(
																							'.sidebar .sidebar-wrapper, .main-panel')
																							.perfectScrollbar(
																									'destroy');

																					setTimeout(
																							function() {
																								$(
																										'body')
																										.addClass(
																												'sidebar-mini');

																								md.misc.sidebar_mini_active = true;
																							},
																							300);
																				}

																				// we simulate the window Resize so the charts will get updated in realtime.
																				var simulateWindowResize = setInterval(
																						function() {
																							window
																									.dispatchEvent(new Event(
																											'resize'));
																						},
																						180);

																				// we stop the simulation of Window Resize after the animations are completed
																				setTimeout(
																						function() {
																							clearInterval(simulateWindowResize);
																						},
																						1000);

																			});
														});
									});
				</script>
</body>

</html>