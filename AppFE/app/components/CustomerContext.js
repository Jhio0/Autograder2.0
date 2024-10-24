import React, { createContext, useState, useEffect, useContext } from "react";
import { useUser } from "@clerk/clerk-react";
import { BASE_URL } from "../constants";

const CustomerContext = createContext();

export const CustomerProvider = ({ children }) => {
  const { isSignedIn, isLoaded, user } = useUser();
  const [customerId, setCustomerId] = useState(null);
  const [AdminID, setAdminID] = useState([]);

  useEffect(() => {
    const fetchCustomerId = async () => {
      if (isLoaded && isSignedIn && user) {
        try {
          const email = user.emailAddresses[0].emailAddress; // Get the user's email from Clerk

          const response = await fetch(`http://localhost:3001/api/students/search?email_address=${email}`, {
            method: "GET",
          });

          if (response.ok) {
            const data = await response.json();
            setCustomerId(data.studentId); // Extract and set the customerId from the response
          } else {
            console.error("Failed to fetch customer ID");
          }
        } catch (error) {
          console.error("Error fetching customer ID:", error);
        }
      }
    };

    fetchCustomerId();
  }, [isLoaded, isSignedIn, user]);
  return (
    <CustomerContext.Provider value={{ customerId}}>
      {children}
    </CustomerContext.Provider>
  );
}
export const useCustomer = () => useContext(CustomerContext);