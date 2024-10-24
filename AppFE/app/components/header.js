import { Link } from "react-router-dom";
import Image from 'next/image'
import { SignedIn, SignedOut, UserButton, SignInButton } from "@clerk/clerk-react";

export default function Header() {
    return (
        <div className="navbar bg-base-100">
            <div className="flex-1">
                <Link to="/" aria-label="Go to home page">
                    <Image
                        src="/autograder_logo.svg"
                        alt="Sunsab Logo"
                        width={150}
                        height={100}
                        loading="eager"
                        priority={true}
                        className="p-1"
                    />
                </Link>
            </div>
            <div className="flex-none">
                <ul className="menu menu-horizontal px-1 text-base">
                    <li>
                        <Link to="/" aria-label="">Home</Link>
                    </li>
                    <li>
                        <Link to="/AssignmentList" aria-label="">Assignments</Link>
                    </li>
                    <li>
                        <SignedOut>
                            <Link to="/SignUpPage" aria-label="">SignUp</Link>
                        </SignedOut>
                        <SignedIn>
                            <UserButton 
                                appearance={{
                                elements: {
                                    avatarBox: {
                                    width:(30),
                                    height:(30),
                                    }
                                }
                                }}
                            />
                        </SignedIn>
                    </li>
                </ul>
            </div>
            
        </div>
    )
}