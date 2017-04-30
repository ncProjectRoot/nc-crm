<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<li>
    <a href="#" class="valign-wrapper menu-element">
        <i class="black-text material-icons">home</i>
        <h2>Home</h2>
    </a>
</li>
<li>
    <a href="#dashboard" class="valign-wrapper menu-element">
        <i class="black-text material-icons">dashboard</i>
        <h2>Dashboard</h2>
    </a>
</li>
<li>
    <a href="#profile" class="valign-wrapper menu-element">
        <i class="black-text material-icons">person_pin</i>
        <h2>My Profile</h2>
    </a>
</li>
<sec:authorize access="hasAnyRole('ROLE_CUSTOMER', 'ROLE_CSR')">
    <li>
        <a href="#order" class="valign-wrapper menu-element">
            <i class="black-text material-icons">receipt</i>
            <h2>Order</h2>
        </a>
    </li>
    <li>
        <a href="#products" class="valign-wrapper menu-element">
            <i class="black-text material-icons">view_list</i>
            <h2>Products</h2>
        </a>
    </li>
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
    <li>
        <a href="#create" class="valign-wrapper menu-element">
            <i class="black-text material-icons">note_add</i>
            <h2>Create</h2>
        </a>
    </li>
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR', 'ROLE_PMG')">
    <li>
        <a href="#search" class="valign-wrapper menu-element">
            <i class="black-text material-icons">search</i>
            <h2>Search</h2>
        </a>
    </li>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <li>
        <a href="#profit" class="valign-wrapper menu-element">
            <i class="black-text material-icons">business</i>
            <h2>Profit</h2>
        </a>
    </li>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_CUSTOMER')">
    <li>
        <a href="#payment" class="valign-wrapper menu-element">
            <i class="black-text material-icons">payment</i>
            <h2>Payment</h2>
        </a>
    </li>
    <li>
        <a href="#complaint" class="valign-wrapper menu-element">
            <i class="black-text material-icons">thumb_down</i>
            <h2>Complaint</h2>
        </a>
    </li>
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_PMG', 'ROLE_CUSTOMER')">
    <li>
        <a href="#complaint" class="valign-wrapper menu-element">
            <i class="black-text material-icons">thumb_down</i>
            <h2>Complaint</h2>
        </a>
    </li>
</sec:authorize>