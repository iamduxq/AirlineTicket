// Simple HTML Include - Pure JavaScript
// This script will include header and footer into pages

// Function to include HTML content
function includeHTML() {
    const elements = document.querySelectorAll('[data-include]');
    
    elements.forEach(async (element) => {
        const file = element.getAttribute('data-include');
        try {
            const response = await fetch(file);
            if (response.ok) {
                const html = await response.text();
                element.innerHTML = html;
                
                // After loading, update navigation if it's header
                if (file.includes('header')) {
                    updateNavigation();
                }
            } else {
                console.error(`Failed to load ${file}`);
            }
        } catch (error) {
            console.error(`Error loading ${file}:`, error);
        }
    });
}

// Function to update navigation paths and active states
function updateNavigation() {
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('.nav-menu a');
    
    navLinks.forEach(link => {
        // Remove active class
        link.classList.remove('active');
        
        // Update paths based on current location
        const href = link.getAttribute('href');
        if (href) {
            if (currentPath.includes('/pages/')) {
                // We're in pages folder
                if (href === 'index.html') {
                    link.setAttribute('href', '../index.html');
                } else if (!href.startsWith('pages/') && !href.startsWith('../')) {
                    link.setAttribute('href', '../' + href);
                }
            } else {
                // We're in root folder
                if (href.startsWith('../')) {
                    link.setAttribute('href', href.replace('../', ''));
                }
            }
            
            // Set active class
            const linkPath = link.getAttribute('href');
            if (currentPath.includes(linkPath) || 
                (currentPath === '/' && linkPath === 'index.html') ||
                (currentPath.includes('index.html') && linkPath === 'index.html')) {
                link.classList.add('active');
            }
        }
    });
    
    // Initialize mobile menu
    initMobileMenu();
}

// Initialize mobile menu toggle
function initMobileMenu() {
    const navToggle = document.querySelector('.nav-toggle');
    const navMenu = document.querySelector('.nav-menu');
    
    if (navToggle && navMenu) {
        navToggle.addEventListener('click', () => {
            navMenu.classList.toggle('active');
        });
    }
}

// Run when DOM is loaded
document.addEventListener('DOMContentLoaded', includeHTML);
