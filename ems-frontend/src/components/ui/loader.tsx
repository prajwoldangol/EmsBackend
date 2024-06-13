import React from 'react'

const FullscreenLoader: React.FC = () => {
  return (
    <div className='fixed inset-0 z-50 flex items-center justify-center bg-white bg-opacity-75 dark:bg-black dark:bg-opacity-75'>
      <span className='sr-only'>Loading...</span>
      <div className='h-8 w-8 animate-bounce rounded-full bg-black [animation-delay:-0.3s] dark:bg-white'></div>
      <div className='h-8 w-8 animate-bounce rounded-full bg-black [animation-delay:-0.15s] dark:bg-white'></div>
      <div className='h-8 w-8 animate-bounce rounded-full bg-black dark:bg-white'></div>
    </div>
  )
}

export default FullscreenLoader
