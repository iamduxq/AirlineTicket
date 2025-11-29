function toggleMenu() {
    const submenu = document.getElementById("submenu");
    const icon = document.getElementById("icon-arrow");
    submenu.classList.toggle("hidden");
    icon.classList.toggle("rotate-180");
}