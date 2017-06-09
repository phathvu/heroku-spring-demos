<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <jsp:include page="header.jsp" />
    <body>
    <div class="container" style="margin-top: 60px;">
        <div class="row">
            <div class="col-xs-12 col-sm-8 col-md-6">
                <form:form action="search" method="get">
                    <div class="input-group">
                        <input name="searchInput" type="text" class="form-control" placeholder="Search by name or author..."/>
                        <span class="input-group-btn">
                            <button class="btn btn-primary" type="submit">Search</button>
                        </span>
                    </div>
                </form:form>
            </div>

            <div class="col-xs-12 col-sm-12 col-md-10">
                <c:if test="${not empty bookList}">
                    <table class="table table-condensed table-hover">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Author</th>
                            <th>ISBN</th>
                            <th>Price</th>
                            <th>Publish Date</th>
                            <th>Category</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="book" items="${bookList}" varStatus="index">
                                <tr>
                                    <td>${book.id}</td>
                                    <td>${book.name}</td>
                                    <td>${book.author}</td>
                                    <td>${book.bookDetails.isbn}</td>
                                    <td>${book.bookDetails.price}</td>
                                    <td>${book.bookDetails.publishDate}</td>
                                    <td>${book.category.name}</td>
                                    <td>
                                        <button class="btn btn-sm btn-primary" onclick="location.href='edit/${book.id}'">Edit</button>
                                        <button class="btn btn-sm btn-danger" onclick="location.href='delete/${book.id}'">Delete</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${bookList.size() == 0}">
                    <br>
                    <div class="alert alert-warning">
                        There is no data
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </body>
</html>
