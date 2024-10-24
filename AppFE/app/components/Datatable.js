import React from 'react';

const DataTable = ({ rawData }) => {
  if (!rawData || rawData === "No actual result for this query") {
    return <div>No results available for this query.</div>;
  }

  // Function to parse the raw string data into table rows and cells
  const parseTableData = (data) => {
    // Split the data into rows based on line breaks and filter out empty or invalid rows
    const rows = data.split('\r\n').filter(row => row.includes('|') && row.trim() !== '');
  
    // Split each row into cells, trimming spaces and handling trailing pipes
    return rows.map(row => row.replace(/\|\s*$/, '').split('|').map(cell => cell.trim()));
  };

  // Use the function to parse the rawData into tableData
  const tableData = parseTableData(rawData);
console.log("this is table data" +tableData);
  return (
    <div className="overflow-x-auto">
    <table className="table table-zebra table-md">
      <thead>
        <tr>
          {tableData[0].map((header, index) => (
            <th key={index}>{header || 'N/A'}</th>
          ))}
        </tr>
      </thead>
      <tbody>
        {tableData.slice(1).map((row, rowIndex) => (
          <tr key={rowIndex}>
            {row.map((cell, cellIndex) => (
              <td key={cellIndex}>{cell || 'N/A'}</td>
            ))}
          </tr>
        ))}
      </tbody>
     
    </table>
  </div>
  );
};
export default DataTable;