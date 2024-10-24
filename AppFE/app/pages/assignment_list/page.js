import React from "react";
import Link from "next/link";
import { FaFileDownload } from "react-icons/fa";

const AssignmentList = () => {
  return (
    <div className="flex flex-col mt-8 items-center h-[90vh]">
      <div className="my-8">
        <h1 className="text-3xl font-bold border p-4 rounded-xl px-12 bg-[#FED136] shadow-2xl">
          Assignment Lists
        </h1>
      </div>

      <div className="gap-10 text-lg font-semibold grid grid-cols-1 md:grid-cols-3 w-full px-4 max-w-[1200px]">
        <div className="bg-white p-6 rounded-xl shadow-lg flex flex-col items-start border hover:shadow-2xl transition-shadow duration-300 bg-gradient-to-t from-yellow-200 to-white">
          <h1 className="text-xl mb-2 border-b-2 border-black w-full">
            Assignment 1: Simple SELECT and SORTING
          </h1>
          <p className="text-gray-700 mb-1">Due date: July 18, 2024</p>
          <p className="text-gray-700 mb-4">Weight: 5%</p>
          <div className="flex-grow" />
          <div className="self-end flex items-center space-x-4">
            <a
              href="/assignment1.docx"
              download="Assignment1.docx"
              className="flex items-center hover:bg-[#EFBB35] hover:scale-105 bg-[#FED136] rounded-md p-2 px-4 border transition-colors duration-300"
            >
              Download <FaFileDownload className="ml-2" />
            </a>
            <Link
              href="/Assignment1"
              className="hover:bg-[#EFBB35] hover:scale-105 bg-[#FED136] rounded-md p-2 px-4 border transition-colors duration-300"
            >
              View
            </Link>
          </div>
        </div>
        <div className="bg-white p-6 rounded-xl shadow-lg flex flex-col items-start border hover:shadow-2xl transition-shadow duration-300 bg-gradient-to-t from-yellow-200 to-white">
          <h1 className="text-xl mb-2 border-b-2 border-black w-full">
            Assignment 2: JOINS
          </h1>
          <p className="text-gray-700 mb-1">Due date: July 18, 2024</p>
          <p className="text-gray-700 mb-4">Weight: 5%</p>
          <div className="flex-grow" />
          <div className="self-end flex items-center space-x-4">
            <a
              href="/assignment2.docx"
              download="Assignment2.docx"
              className="flex items-center hover:bg-[#EFBB35] hover:scale-105 bg-[#FED136] rounded-md p-2 px-4 border transition-colors duration-300"
            >
              Download <FaFileDownload className="ml-2" />
            </a>
            <Link
              href="/Assignment2"
              className="hover:bg-[#EFBB35] hover:scale-105 bg-[#FED136] rounded-md p-2 px-4 border transition-colors duration-300"
            >
              View
            </Link>
          </div>
        </div>
        <div className="bg-white p-6 rounded-xl shadow-lg flex flex-col items-start border hover:shadow-2xl transition-shadow duration-300 bg-gradient-to-t from-yellow-200 to-white">
          <h1 className="text-xl mb-2 border-b-2 border-black w-full">
            Assignment 3: Single Row
          </h1>
          <p className="text-gray-700 mb-1">Due date: July 18, 2024</p>
          <p className="text-gray-700 mb-4">Weight: 5%</p>
          <div className="flex-grow" />
          <div className="self-end flex items-center space-x-4">
            <a
              href="/assignment1.docx"
              download="Assignment1.docx"
              className="flex items-center hover:bg-[#EFBB35] hover:scale-105 bg-[#FED136] rounded-md p-2 px-4 border transition-colors duration-300"
            >
              Download <FaFileDownload className="ml-2" />
            </a>
            <Link
              href="/pages/submit3"
              className="hover:bg-[#EFBB35] hover:scale-105 bg-[#FED136] rounded-md p-2 px-4 border transition-colors duration-300"
            >
              View
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AssignmentList;
