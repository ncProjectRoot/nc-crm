<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_PMG', 'ROLE_CSR')">
<li>
    <a href="#dashboard" class="valign-wrapper menu-element">
        <i class="white-text material-icons">dashboard</i>
        <h2>Dashboard</h2>
    </a>
</li>
</sec:authorize>
<li>
    <a href="#profile" class="valign-wrapper menu-element">
        <i class="white-text material-icons">person_pin</i>
        <h2>My Profile</h2>
    </a>
</li>
<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_CSR')">
    <li>
        <a href="#orders" class="valign-wrapper menu-element">
            <i class="white-text material-icons">add_shopping_cart</i>
            <h2>Orders</h2>
        </a>
    </li>
    <li>
        <a href="#products" class="valign-wrapper menu-element">
            <i class="white-text material-icons">view_list</i>
            <h2>Products</h2>
        </a>
    </li>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_CUSTOMER')">
    <c:if test="${user.isContactPerson()}">
        <li>
            <a href="#organization" class="valign-wrapper menu-element">
                <i class="white-text material-icons">face</i>
                <h2>Organization</h2>
            </a>
        </li>
    </c:if>
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
    <li>
        <a href="#users" class="valign-wrapper menu-element">
            <i class="white-text material-icons">face</i>
            <h2>Users</h2>
        </a>
    </li>
    <li>
        <a href="#discounts" class="valign-wrapper menu-element">
            <i class="white-text material-icons">loyalty</i>
            <h2>Discounts</h2>
        </a>
    </li>
    <li>
        <a href="#groups" class="valign-wrapper menu-element">
            <i class="white-text material-icons">bubble_chart</i>
            <h2>Groups</h2>
        </a>
    </li>
    <li>
        <a href="#regions" class="valign-wrapper menu-element">
            <i class="white-text material-icons">public</i>
            <h2>Regions</h2>
        </a>
    </li>
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_PMG', 'ROLE_CUSTOMER', 'ROLE_ADMIN')">
    <li>
        <a href="#complaints" class="valign-wrapper menu-element">
            <i class="white-text material-icons">thumb_down</i>
            <h2>Complaints</h2>
        </a>
    </li>
</sec:authorize>