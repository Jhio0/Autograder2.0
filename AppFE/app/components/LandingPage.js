import Image from "next/image";

const LandingPage = () => {
  return (
    <>
      <section className="flex flex-col md:flex-row items-center justify-between py-20 px-4 md:px-10 lg:px-20">
        <div className="md:w-1/2 text-left mb-10 md:mb-0 ml-6">
          <h2 className="text-4xl md:text-5xl font-extrabold text-gray-800 mb-6 leading-tight">
            Revolutionize Your{" "}
            <span className="text-[#FED136]">Grading Process</span>
          </h2>
          <p className="text-lg md:text-xl text-gray-600 mb-8 max-w-lg">
            Automate programming assignment grading with precision and ease,
            saving time and enhancing feedback quality.
          </p>
          <button className="bg-[#FED136] hover:bg-[#EFBB35] text-black font-bold py-3 px-8 rounded-full transition duration-300 transform hover:scale-105 shadow-lg">
            <a href="/pages/assignment_list" className="flex items-center">
              Get Started
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="h-5 w-5 ml-2"
                viewBox="0 0 20 20"
                fill="currentColor"
              >
                <path
                  fillRule="evenodd"
                  d="M10.293 3.293a1 1 0 011.414 0l6 6a1 1 0 010 1.414l-6 6a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-4.293-4.293a1 1 0 010-1.414z"
                  clipRule="evenodd"
                />
              </svg>
            </a>
          </button>
        </div>
        <div className="md:w-1/2 flex justify-center">
          <img
            src="/computer.png"
            alt="Autograder illustration"
            className="w-full max-w-xl"
          />
          {/* TODO: Update to the next image component*/}
        </div>
      </section>

      <section className="grid md:grid-cols-3 gap-8 py-16 mx-20">
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h3 className="text-2xl font-semibold text-gray-800 mb-4">
            For Teachers
          </h3>
          <p className="text-gray-600 mb-4">
            Upload assignments and JUNIT tests effortlessly. Streamline your
            grading process.
          </p>
          <a href="#" className="text-blue-500 hover:underline">
            Learn more &rarr;
          </a>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h3 className="text-2xl font-semibold text-gray-800 mb-4">
            For Students
          </h3>
          <p className="text-gray-600 mb-4">
            Access assignments, submit work, and receive instant feedback on
            your code.
          </p>
          <a href="#" className="text-blue-500 hover:underline">
            Learn more &rarr;
          </a>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h3 className="text-2xl font-semibold text-gray-800 mb-4">
            Automated Grading
          </h3>
          <p className="text-gray-600 mb-4">
            JUNIT tests automatically evaluate submissions, providing
            pass/fail outcomes.
          </p>
          <a href="#" className="text-blue-500 hover:underline">
            Learn more &rarr;
          </a>
        </div>
      </section>

      <section className="text-center py-20">
        <h2 className="text-4xl font-bold text-gray-800 mb-4">
          Ready to Transform Your Grading Experience?
        </h2>
        <p className="text-xl text-gray-600 mb-8">
          Join thousands of educators and students benefiting from Autograder
        </p>
        <button className="bg-[#FED136] hover:bg-[#EFBB35] text-black font-bold py-3 px-6 rounded-full transition duration-300 transform hover:scale-105">
          <a href="#assignment">Start Free Trial</a>
        </button>
      </section>
    </>
  );
};

export default LandingPage;

{/* <div className="h-full bg-gradient-to-t from-yellow-100 to-white"> */ }