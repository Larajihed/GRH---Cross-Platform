$('#search-input').on('input', function() {
    var searchTerm = $(this).val();
    $.ajax({
      url: '/search',
      method: 'POST',
      data: { term: searchTerm },
      success: function(response) {
        // Handle successful response here
        // For example, update a table with the search results
        $('#search-results').empty();
        $.each(response, function(index, result) {
          $('#search-results').append('<tr><td>' + result.name + '</td><td>' + result.description + '</td></tr>');
        });
      },
      error: function(xhr, status, error) {
        // Handle error here
      }
    });
  });
  