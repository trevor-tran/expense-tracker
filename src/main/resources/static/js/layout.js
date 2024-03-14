function setActiveLink() {
    const currentPath = window.location.pathname;

    const activeLink = $("#navbar").find(`a[href='${currentPath}'`);

    activeLink.addClass("active");
    activeLink.find("i, span").css({
        "color": "white"
    })
}
