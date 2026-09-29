document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.auto-dismiss').forEach(function (alertEl) {
        setTimeout(function () {
            alertEl.style.opacity = '0';
            alertEl.style.transition = 'opacity 0.4s ease';
            setTimeout(function () {
                alertEl.remove();
            }, 400);
        }, 4000);
    });

    const menuToggle = document.querySelector('.menu-toggle');
    const sidebar = document.querySelector('.sidebar');

    if (menuToggle && sidebar) {
        menuToggle.addEventListener('click', function () {
            sidebar.classList.toggle('d-none');
        });
    }
});
