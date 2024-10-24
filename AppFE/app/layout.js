import { Inter } from "next/font/google";
import "./globals.css";

import Footer from "./components/Footer";
import Home from "./page";
import {
  ClerkProvider,
  SignInButton,
  SignedIn,
  SignedOut,
  UserButton
} from '@clerk/nextjs'

const inter = Inter({ subsets: ["latin"] });

export const metadata = {
  title: 'Autograder by Sunsab',
  description: 'Streamlining the grading process for SQL files. Accurate, efficient, and reliable assessment tool for SQL education.',
  authors: [{ name: 'Sunsab Team' }],
  creator: 'Sunsab',
  publisher: 'Sunsab',
  icons: {
    icon: [
      { url: '/favicon-32x32.png', sizes: '32x32', type: 'image/png' },
      { url: '/favicon-16x16.png', sizes: '16x16', type: 'image/png' },
      { url: '/favicon.ico', sizes: 'any' }
    ],
    apple: [
      { url: '/apple-touch-icon.png', sizes: '180x180', type: 'image/png' }
    ],
    other: [
      { rel: 'icon', url: '/android-chrome-192x192.png', sizes: '192x192', type: 'image/png' },
      { rel: 'icon', url: '/android-chrome-512x512.png', sizes: '512x512', type: 'image/png' }
    ]
  },
  openGraph: {
    title: 'Autograder by Sunsab',
    description: 'Streamlining the grading process for SQL files. Accurate, efficient, and reliable assessment tool for SQL education..',
    url: 'https://autograder.sunsab.com',
    siteName: 'Autograder',
    images: [
      {
        url: 'https://autograder.sunsab.com/autograder-card.png',
        width: 800,
        height: 400,
        alt: 'A pen and keyboard on a stylized yellow background, with the Sunsab Autograder logo in the middle.',
      },
    ],
    locale: 'en_US',
    type: 'website',
  },
  twitter: {
    card: 'summary_large_image',
    title: 'Autograder by Sunsab',
    description: 'Streamlining the grading process for SQL files. Accurate, efficient, and reliable assessment tool for SQL education.',
    images: ['https://autograder.sunsab.com/autograder-card.png'],
  },
};

export default function RootLayout({ children }) {
  return (
    <ClerkProvider publishableKey={process.env.NEXT_PUBLIC_CLERK_PUBLISHABLE_KEY} afterSignOutUrl="/"
       
    >
      <html lang="en" data-theme="bumblebee">
        <body className={`${inter.className} min-h-screen flex flex-col`}>
          <Home/>
          <Footer/>
        </body>
      </html>
    </ClerkProvider>
  );
}
