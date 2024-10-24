"use client";
import { useState, useEffect } from "react";
import { FaFileUpload } from "react-icons/fa";
import { FaCircleCheck } from "react-icons/fa6";
import { IoFileTrayFull } from "react-icons/io5";
import { ImCross } from "react-icons/im";
import axios from "axios"; // Make sure axios is imported
import { assignments } from "../constants";
import DataTable from "./Datatable";
import IDE from "./IDE";
import { useClerk } from "@clerk/nextjs";

import { format } from 'date-fns';
import { useCustomer } from './CustomerContext';

export default function AssignmentComponent({selectedAssignment, assignmentNumber}) {
    const [file, setFile] = useState(null);
    const [sqlCode, setSqlCode] = useState(""); 
    const [uploadMessage, setUploadMessage] = useState("Drag and Drop File to Submit");
    const [submitted, setSubmitted] = useState(false);
    const [dragActive, setDragActive] = useState(false);
    const [testResults, setTestResults] = useState([]);
    //logs for the info from backend
    const [progress, setProgress] = useState(0);
    const [failures, setFailures] = useState(0);
    const [pointsObtained, setPointsObtained] = useState(0);
    const [totalPoints, setTotalPoints] = useState(0);
  
    const clerk = useClerk();

    const [submissionDate, setSubmissionDate] = useState('');
    const [fileSubmission, setFileSubmission] = useState('');
    const [totalGrade, setTotalGrade] = useState('');
    const [studentId, setStudentId] = useState('');
    const [assignmentId, setAssignmentId] = useState('');

    const { customerId, loading: contextLoading } = useCustomer();
  
    useEffect(() => {
      if (clerk.loaded) {
        // Initialize Monaco Editor or any other logic here
      }
    }, [clerk.loaded]);
  
    const handleDragOver = (e) => {
      e.preventDefault();
      e.stopPropagation();
      setDragActive(true);
    };
  
    const handleDragLeave = (e) => {
      e.preventDefault();
      e.stopPropagation();
      setDragActive(false);
    };
  
    const handleDrop = (e) => {
      e.preventDefault();
      e.stopPropagation();
      setDragActive(false);
      const uploadedFile = e.dataTransfer.files[0];
      setFile(uploadedFile);
      setUploadMessage("File uploaded, Ready to Submit");
      setSubmitted(false);
    };
  
    const handleFileChange = (e) => {
      const uploadedFile = e.target.files[0];
      setFile(uploadedFile);
      setUploadMessage("File uploaded, Ready to Submit");
      setSubmitted(false);
    };
  
    const handleSubmit = async () => {
      const formData = new FormData();
  
      // If the SQL code is provided from the editor, create a Blob
      if (sqlCode) {
        const blob = new Blob([sqlCode], { type: "text/sql" });
        formData.append("file", blob, "editor.sql"); // Using a default name
      } else if (file) {
        formData.append("file", file);
      } else {
        alert("Please enter SQL code or select a file to upload");
        return;
      }
  
      try {
        console.log("Starting file upload...");
        const response = await axios.post(
          `http://localhost:3001/api/submission/${selectedAssignment}`,
          formData,
          {
            headers: { "Content-Type": "multipart/form-data" },
          }
        );
  
        const data = response.data;
        console.log("Server response:", data);
  
        if (data.error) {
          console.error(data.error);
          alert(data.error);
          return;
        }
        const testResultsData = data.testResultData || [];
        const formattedTestResults = testResultsData.map((query) => {
          const queryKey = Object.keys(query)[0];
          const results = query[queryKey];
  
          return {
            name: queryKey,
            order_match: results.order_match,
            content_match: results.content_match,
            row_count_match: results.row_count_match,
            column_count_match: results.column_count_match,
            column_names_match: results.column_names_match,
            column_names_score: results.column_names_score,
            column_order_match: results.column_order_match,
            actualResultTable: results.actualResultTable,
            expectedResultTable: results.expectedResultTable,
          };
        });
  
        // Update states with submission results
        const gradePercentage = parseFloat(data.gradePercentage.toFixed(2));
        setProgress(gradePercentage);
        setFailures(data.failures || 0);
        setTestResults(formattedTestResults);
        setPointsObtained(data.points_obtained || 0);
        setTotalPoints(data.total_points || 0);
        setTotalGrade(gradePercentage);
        setStudentId(customerId);
        setAssignmentId(assignmentNumber);

        // Set submission date
        const formattedDate = format(new Date(), 'yyyy-MM-dd');
        setSubmissionDate(formattedDate);
        console.log(file);
        // Create submissionData object with the correct values
        const submissionData = {
            submissionDate: formattedDate,
            fileSubmission: sqlCode ? new Blob([sqlCode], { type: "text/sql" }) : file,
            totalGrade: gradePercentage,
            studentId: customerId,
            assignmentId: assignmentNumber,
        };

        console.log(submissionData)
        
        const responseSubmit = await axios.post('http://localhost:3001/api/submissions', submissionData,
          {
            headers: { "Content-Type": "multipart/form-data" },
          }
        );
            console.log('Submission successful:', responseSubmit.data);


  
        setSubmitted(true);
      } catch (error) {
        console.error("Error uploading submission file:", error);
        alert("There was an unexpected error. Please try again.");
      }



    };
  
    const generateSummary = () => {
      const percentagePassed = progress;
      // const failedTests = testResults.failures;
      // const totalTests = testResults.length;
      // const passedTests = totalTests - failedTests;
  
      const testResultMessage =
        `${percentagePassed}% of tests passed. ` +
        `Score: ${pointsObtained}/${totalPoints}`;
  
      return testResultMessage;
    };
  
    const assignment = assignments[assignmentNumber];
  
    return (
      <>
        <h1 className="text-4xl px-8 pb-8 font-bold mb-4 ">
          {assignment.assignmentTitle}
        </h1>
  
        <div className="flex flex-col lg:flex-row gap-8 max-w-[1200px]">
          <div className="flex flex-col w-full lg:w-1/2 gap-8">
            <div className="text-md p-4 border border-base-300 rounded-xl shadow-xl flex flex-col gap-2 bg-white">
              <h2 className="text-lg font-bold pb-2">Instructions</h2>
              {assignment.instructions.map((instruction, index) => (
                <p key={index}>{instruction}</p>
              ))}
            </div>
            <div className="text-md p-4 border border-base-300 rounded-xl shadow-xl flex flex-col gap-2 bg-white">
              <h2 className="text-lg font-bold pb-2">Remember</h2>
              {assignment.tips.map((tip, index) => (
                <p key={index}>{tip}</p>
              ))}
            </div>
            <div className="text-center border border-gray-300 p-4 rounded-xl shadow-xl">
            {testResults.map((test, index) => (
              <div key={index} className="flex flex-col items-start mb-4 w-full">
                <div className="font-bold mb-2">Test {index + 1} Results:</div>
  
                <div className="w-full mb-4">
                  <div className="font-semibold">Expected Result:</div>
                  <DataTable rawData={test.expectedResultTable} />
                </div>
  
                <div className="w-full mb-4">
                  <div className="font-semibold">Actual Result:</div>
                  <DataTable rawData={test.actualResultTable} />
                </div>
              </div>
            ))}
          </div>
          </div>
  
          <div className="flex flex-col w-full lg:w-1/2 gap-8">
            <div
              className={`flex flex-col justify-center items-center border border-gray-300 rounded-xl shadow-xl p-10 cursor-pointer ${
                dragActive
                  ? "bg-base-200"
                  : file
                  ? "bg-green-600 text-white"
                  : "bg-base-100"
              }`}
              onClick={() => document.getElementById("fileInput").click()}
              onDragOver={handleDragOver}
              onDragLeave={handleDragLeave}
              onDrop={handleDrop}
            >
              <FaFileUpload size={80} />
              <p className="text-2xl mt-4">{uploadMessage}</p>
            </div>
            {file && (
              <p className=" text-xl rounded-xl shadow-xl flex items-center justify-center border border-gray-300 p-4">
                <IoFileTrayFull className="mr-2" />
                {file.name}
                <FaCircleCheck className="text-green-600 ml-2" />
              </p>
            )}
  
            <input
              id="fileInput"
              type="file"
              onChange={handleFileChange}
              style={{ display: "none" }}
            />
          {/* Submit button for uploaded file */}
            {file && !submitted && (
              <button
                className="btn btn-primary text-xl text-black"
                onClick={handleSubmit}
              >
                Submit
              </button>
            )}
            {clerk.loaded ? (
                <IDE setSqlCode={setSqlCode}/>
              ) : (
                <div>Loading...</div>
            )}
                  
            {/* Submit button for SQL code from the editor */}
            {sqlCode && !submitted && (
            <button
              className="btn btn-primary text-xl text-black mt-4"
              onClick={handleSubmit}
            >
              Submit SQL Code
            </button>
            )}
            
            {submitted && (
              <div className="text-center border border-gray-300 p-4 rounded-xl shadow-xl">
                <p className="text-3xl border border-green-300 p-4 font-semibold bg-green-600 text-white rounded-md">
                  Grade : {progress}%
                </p>
                <div className="p-6 ">
                  {testResults.map((test, index) => (
                    <div key={index} className="flex flex-col items-start mb-4">
                      <p className="text-lg font-bold">{test.name}</p>
                      <p className="flex items-center text-lg mb-2">
                        Row_Order_Match:
                        {test.order_match ? (
                          <FaCircleCheck className="text-green-600 ml-2" />
                        ) : (
                          <ImCross className="text-red-600 ml-2" />
                        )}
                      </p>
                      <p className="flex items-center text-lg mb-2">
                      content_match:
                        {test.content_match ? (
                          <FaCircleCheck className="text-green-600 ml-2" />
                        ) : (
                          <ImCross className="text-red-600 ml-2" />
                        )}
                      </p>
                      <p className="flex items-center text-lg mb-2">
                        Number of Row:
                        {test.row_count_match ? (
                          <FaCircleCheck className="text-green-600 ml-2" />
                        ) : (
                          <ImCross className="text-red-600 ml-2" />
                        )}
                      </p>
                      <p className="flex items-center text-lg mb-2">
                        Number of Columns:
                        {test.column_count_match ? (
                          <FaCircleCheck className="text-green-600 ml-2" />
                        ) : (
                          <ImCross className="text-red-600 ml-2" />
                        )}
                      </p>
                      <p className="flex items-center text-lg mb-2">
                        Column Name Match:
                        {test.column_names_match? (
                          <FaCircleCheck className="text-green-600 ml-2" />
                        ) : (
                          <ImCross className="text-red-600 ml-2" />
                        )}
                        
                          <li>{test.column_names_score}</li>
                        
                      </p>
                      <p className="flex items-center text-lg mb-2">
                        Column Order:
                        {test.column_order_match? (
                          <FaCircleCheck className="text-green-600 ml-2" />
                        ) : (
                          <ImCross className="text-red-600 ml-2" />
                        )}
                      </p>
                    </div>
                  ))}
                </div>
                <div>
                  <h2 className="text-2xl font-semibold">Summary</h2>
                  <p className="text-lg">{generateSummary()}</p>
                </div>
                
              </div>
              
            )}
          </div>
          
        </div>
      </>
    );
}