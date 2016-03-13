<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Electronics store - Edit</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="shortcut icon" href="/png/icon.png">
    <style>
        .navbar {
            margin-bottom: 10px;
            border-radius: 0;
        }

        .jumbotron {
            margin-bottom: 0;
            padding: 10px;

            position: relative;
            background-image: url('/png/Electronics.png');
            color: white;
        }

        footer {
            background-color: #f2f2f2;
            padding: 20px;
        }
    </style>
</head>
<body>

<div class="jumbotron">
    <div class="container text-center">
        <h1>Electronics Store</h1>
    </div>
</div>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li><a href="/"><span class="glyphicon glyphicon-home"></span></a></li>

                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Products
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li class="dropdown-header" style="color:#337AB7;"><strong>Categories:</strong></li>
                        <li class="divider"></li>
                        <c:forEach items="${categories}" var="category">
                            <li><a href="/category/${category.id}">${category.name}</a></li>
                        </c:forEach>
                    </ul>
                </li>
                <li><a href="/about">About</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/cart"><span class="glyphicon glyphicon-shopping-cart"></span> Cart </a></li>
                <li><a href="/edit"><span class="glyphicon glyphicon-wrench"></span> Settings </a></li>
                <c:choose>
                    <c:when test="${not empty pageContext.request.remoteUser}">
                        <li><a href="/logout"><span class="glyphicon glyphicon-log-out"></span> Logout </a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="/login"><span class="glyphicon glyphicon-log-in"></span> Sing In </a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <form role="form" enctype="multipart/form-data" class="form-horizontal" style="width:30%;"
          action="/edit/product/add" method="post">
        <div class="form-group"><h3>Add new product</h3></div>
        <select class="selectpicker form-control form-group" name="category">
            <c:forEach items="${categories}" var="category">
                <option value="${category.id}">${category.name}</option>
            </c:forEach>
        </select>
        <input class="form-control form-group" type="text" name="name" placeholder="Name">
        <input class="form-control form-group" type="text" name="description" placeholder="Description">
        <input class="form-control form-group" type="text" name="price" placeholder="Price">

        <div class="form-group"><input type="submit" class="btn btn-primary" value="Ok"></div>
    </form>
</div>
<hr>

<div class="container">
    <form role="form" enctype="multipart/form-data" class="form-horizontal" style="width:27%;"
          action="/edit/category/add" method="post">
        <div class="form-group"><h3>Add new category</h3></div>
        <div class="form-group"><input type="text" class="form-control" name="name" placeholder="Name"></div>
        <div class="form-group"> Picture: <input type="file" name="picture"></div>
        <div class="form-group"><input type="submit" class="btn btn-primary" value="Ok"></div>
    </form>
</div>
<hr>

<div class="container">
    <form role="form" enctype="multipart/form-data" class="form-horizontal" style="width:30%;"
          action="/edit/product/delete" method="post">
        <div class="form-group"><h3>Delete product</h3></div>
        <select class="selectpicker form-control form-group" name="product">
            <c:forEach items="${products}" var="product">
                <option value="${product.id}">${product.name}</option>
            </c:forEach>
        </select>

        <div class="form-group"><input type="submit" class="btn btn-primary" value="Ok"></div>
    </form>
</div>
<hr>

<div class="container">
    <form role="form" enctype="multipart/form-data" class="form-horizontal" style="width:30%;"
          action="/edit/category/delete" method="post">
        <div class="form-group"><h3>Delete category</h3></div>
        <select class="selectpicker form-control form-group" name="category">
            <c:forEach items="${categories}" var="category">
                <option value="${category.id}">${category.name}</option>
            </c:forEach>
        </select>

        <div class="form-group"><input type="submit" class="btn btn-primary" value="Ok"></div>
    </form>
</div>
<hr>

<div class="container">
    <div><h3>Orders list</h3></div>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th>Product name</th>
            <th>Product price</th>
            <th>Client name</th>
            <th>Client e-mail</th>
            <th>Order date</th>
        </tr>
        </thead>

        <c:set var="counter" value="${0}"/>
        <c:forEach items="${orders}" var="order">
            <c:set var="counter" value="${counter + 1}"/>

            <td>${counter}</td>

            <c:choose>
                <c:when test="${empty order.product.name}">
                    <td>Not available</td>
                </c:when>
                <c:otherwise>
                    <td>${order.product.name}</td>
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${empty order.product.price}">
                    <td>Not available</td>
                </c:when>
                <c:otherwise>
                    <td>${order.product.price}</td>
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${empty order.client.name}">
                    <td>Not available</td>
                </c:when>
                <c:otherwise>
                    <td>${order.client.name}</td>
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${empty order.client.email}">
                    <td>Not available</td>
                </c:when>
                <c:otherwise>
                    <td>${order.client.email}</td>
                </c:otherwise>
            </c:choose>
            <td>${order.date}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<br>

<footer class="container-fluid text-center">
    <p>Store Search Bar</p>

    <form class="form-inline" role="search" action="/search" method="post">Looking for:
        <input type="text" class="form-control" name="pattern" size="50" placeholder="Item name">
        <button type="submit" class="btn btn-danger">Search</button>
    </form>
</footer>

</body>
</html>