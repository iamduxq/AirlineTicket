function renderPagination(totalPages, currentPage, baseUrl) {
    const container = document.getElementById("pagination-container");
    if (!container || totalPages <= 1) return;
    let html = `
    <div class="fui-roundedFull-pagination">
        <ul class="pagination-list">
    `;

    html += `
        <li class="pagination-item btn-prev ${currentPage === 0 ? 'disable' : ''}">
            <a ${currentPage === 0 ? '' : `href="${baseUrl}?page=${currentPage - 1}"`} 
               class="pagination-link">
                <svg width="6" height="10" viewBox="0 0 6 10" fill="none">
                    <path fill-rule="evenodd" clip-rule="evenodd" 
                    d="M5.20711 0.792893C5.59763 1.18342 5.59763 1.81658 
                       5.20711 2.20711L2.41421 5L5.20711 7.79289
                       C5.59763 8.18342 5.59763 8.81658 5.20711 9.20711
                       C4.81658 9.59763 4.18342 9.59763 3.79289 9.20711L0.292893 5.70711
                       C-0.0976311 5.31658 -0.0976311 4.68342 0.292893 4.29289
                       L3.79289 0.792893C4.18342 0.402369 4.81658 0.402369 5.20711 0.792893Z"
                    fill="currentColor"></path>
                </svg>
            </a>
        </li>
    `;

    for (let page = 0; page < totalPages; page++) {
        html += `
            <li>
                <a href="${baseUrl}?page=${page}"
                class="pagination-link ${page === currentPage ? 'selected' : ''}">
                ${page + 1}
                </a>
            </li>
        `
    }

    html += `
        <li class="pagination-item btn-next ${currentPage + 1 === totalPages ? 'disabled' : ''}">
            <a ${currentPage + 1 === totalPages ? '' : `href="${baseUrl}?page=${currentPage + 1}"`} 
               class="pagination-link">
                <svg width="6" height="10" viewBox="0 0 6 10" fill="none">
                    <path fill-rule="evenodd" clip-rule="evenodd"
                    d="M0.792893 0.792893C0.402369 1.18342 0.402369 1.81658 
                       0.792893 2.20711L3.58579 5L0.792893 7.79289
                       C0.402369 8.18342 0.402369 8.81658 0.792893 9.20711
                       C1.18342 9.59763 1.81658 9.59763 2.20711 9.20711L5.70711 5.70711
                       C6.09763 5.31658 6.09763 4.68342 5.70711 4.29289
                       L2.20711 0.792893C1.81658 0.402369 1.18342 0.402369 0.792893 0.792893Z"
                    fill="currentColor"></path>
                </svg>
            </a>
        </li>
    `;
    html += `</ul></div>`;
    container.innerHTML = html;
}