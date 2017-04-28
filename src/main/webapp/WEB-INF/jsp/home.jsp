<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .content-body-wrapper {
        height: 400px;
        width: calc(100% - 20px * 2);
        margin: 20px;
        background-color: #fff;
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        justify-content: center;
        align-items: center;
    }

    .welcome-h1 {
        font-size: 3.2rem;
    }

    .welcome-h2 {
        font-size: 2.8rem;
    }
</style>
<div class="content-header z-depth-1 valign-wrapper">
    <i class="black-text material-icons">home</i>
    <span>Home</span>
</div>
<div class="content-body-wrapper z-depth-1">
    <h1 class="welcome-h1">Welcome, <span class="user-name">!</span></h1>
    <h2 class="welcome-h2">Use toolbar for navigation. Good luck!</h2>
</div>
<script>
    $(".welcome-h1 .user-name").html($("#user-name").html())
</script>