"use client";

import { useState } from "react";
import { FaFileUpload } from "react-icons/fa";
import { FaCircleCheck } from "react-icons/fa6";
import { IoFileTrayFull } from "react-icons/io5";
import { ImCross } from "react-icons/im";
import axios from "axios"; // Make sure axios is imported
import { assignments } from "../constants";

export default function Assignment() {
    const [file, setFile] = useState(null);
    const [uploadMessage, setUploadMessage] = useState("Drag and Drop File to Submit");
    const [submitted, setSubmitted] = useState(false);
    const [dragActive, setDragActive] = useState(false);
    const [testResults, setTestResults] = useState([]);
    const [progress, setProgress] = useState(0);
    const [failures, setFailures] = useState(0);


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
        if (!file) {
            alert("Please select a file to upload");
            return;
        }

        const formData = new FormData();
        formData.append("file", file);
        formData.append("unit_test_id", "12345678910"); // TODO: Change this to a dynamic unit test ID

        try {
            console.log("Starting file upload...");
            const response = await axios.post(
                "http://localhost:5000/api/upload_submission",
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
            const testResultsData = data.test_results || [];

            setProgress(data.percentage_passed || 0);
            setFailures(data.failures || 0);
            setTestResults(testResultsData);

            setSubmitted(true);
            // alert("Submission file uploaded and tested successfully"); // Just commented out to speed up demo time
        } catch (error) {
            console.error("Error uploading submission file:", error);
            alert("There was an unexpected error. Please try again."); //Added a popup alert on error
        }
    };

    const generateSummary = () => {
        const percentagePassed = progress;
        const failedTests = failures;
        const totalTests = testResults.length;
        const passedTests = totalTests - failedTests;
        const pointsObtained = passedTests * 5;
        const totalPoints = totalTests * 5;

        const testResultMessage =
            `${percentagePassed}% of tests passed. ${failedTests} tests failed. ` +
            `Score: ${pointsObtained}/${totalPoints}`;

        return testResultMessage;
    };

    const assignment = assignments[2];

    return (
        <>
            <h1 className="text-4xl px-8 pb-8 font-bold mb-4">{assignment.assignmentTitle}</h1>

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
                </div>

                <div className="flex flex-col w-full lg:w-1/2 gap-8">
                    <div
                        className={`flex flex-col justify-center items-center border border-gray-300 rounded-xl shadow-xl p-10 cursor-pointer ${dragActive ? 'bg-base-200' : file ? 'bg-green-600 text-white' : 'bg-base-100'
                            }`}
                        onClick={() => document.getElementById('fileInput').click()}
                        onDragOver={handleDragOver}
                        onDragLeave={handleDragLeave}
                        onDrop={handleDrop}
                    >
                        <FaFileUpload size={80} />
                        <p className="text-2xl mt-4">{uploadMessage}</p>
                    </div>
                    {file && <p className=" text-xl rounded-xl shadow-xl flex items-center justify-center border border-gray-300 p-4"><IoFileTrayFull className="mr-2" />{file.name}<FaCircleCheck className="text-green-600 ml-2" /></p>}

                    <input
                        id="fileInput"
                        type="file"
                        onChange={handleFileChange}
                        style={{ display: "none" }}
                    />
                    {file && !submitted && (
                        <button
                            className="btn btn-primary text-xl text-black"
                            onClick={handleSubmit}
                        >
                            Submit
                        </button>
                    )}
                    {submitted && (
                        <div className="text-center border border-gray-300 p-4 rounded-xl shadow-xl">
                            <p className="text-3xl border border-green-300 p-4 font-semibold bg-green-600 text-white rounded-md">Grade : {progress}%</p>
                            <div className="p-6 ">
                                {testResults.map((test, index) => (
                                    <p key={index} className="flex items-center justify-start text-lg mb-2">
                                        {test.name} {test.status === "passed" ? (
                                            <span><FaCircleCheck className="text-green-600 ml-2" /></span>
                                        ) : (
                                            <span><ImCross className="text-red-600 ml-2" /></span>
                                        )}
                                    </p>
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
