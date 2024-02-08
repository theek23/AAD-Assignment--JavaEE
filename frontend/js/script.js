// Function to show a view
function showView(viewId) {
    // Hide all views
    document.querySelectorAll('.view').forEach(view => {
      view.classList.remove('active');
    });
    
    // Show the selected view
    const view = document.getElementById(viewId);
    if(view) {
      view.classList.add('active');
    }
  }
  
  // Event listeners for menu items
  document.querySelectorAll('#sidebar .menu-item').forEach(item => {
    item.addEventListener('click', function() {
      // Remove active class from all items
      document.querySelectorAll('#sidebar .menu-item').forEach(item => {
        item.classList.remove('active');
      });
      
      // Add active class to clicked item
      this.classList.add('active');
      
      // Get the corresponding view id
      const viewId = this.textContent.trim().toLowerCase() + '-view';
      showView(viewId);
    });
  });
  
  // Show the home view by default
  showView('home-view');
  