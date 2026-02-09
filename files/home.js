// Carousel functionality
let currentSlide = 0;
const indicators = document.querySelectorAll('.indicator');

indicators.forEach((indicator, index) => {
    indicator.addEventListener('click', () => {
        currentSlide = index;
        updateCarousel();
    });
});

function updateCarousel() {
    indicators.forEach((indicator, index) => {
        if (index === currentSlide) {
            indicator.classList.add('active');
        } else {
            indicator.classList.remove('active');
        }
    });
}

// Auto-advance carousel
setInterval(() => {
    currentSlide = (currentSlide + 1) % indicators.length;
    updateCarousel();
}, 5000);

// Search button functionality
document.querySelector('.search-btn')?.addEventListener('click', () => {
    alert('Search functionality coming soon!');
});

// Smooth scroll for navigation links
document.querySelectorAll('.nav a').forEach(link => {
    link.addEventListener('click', (e) => {
        const href = link.getAttribute('href');
        if (href.startsWith('#')) {
            e.preventDefault();
            const target = document.querySelector(href);
            if (target) {
                target.scrollIntoView({ behavior: 'smooth' });
            }
        }
    });
});

// Add animation on scroll for feature cards
const observerOptions = {
    threshold: 0.2,
    rootMargin: '0px 0px -100px 0px'
};

const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.style.opacity = '0';
            entry.target.style.transform = 'translateY(30px)';
            
            setTimeout(() => {
                entry.target.style.transition = 'all 0.6s ease-out';
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
            }, 100);
            
            observer.unobserve(entry.target);
        }
    });
}, observerOptions);

document.querySelectorAll('.feature-card').forEach(card => {
    observer.observe(card);
});

// Profile icon click
document.querySelector('.profile-icon')?.addEventListener('click', () => {
    window.location.href = 'login.html';
});
