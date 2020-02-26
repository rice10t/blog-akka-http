import {useEffect} from "react"

export default function Confirm() {
  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get("code")

    fetch("http://localhost:8080/login_oauth", {
      method: 'POST',
      headers: {
        "Content-Type": "application/json",
      },
      mode: "cors",
      body: JSON.stringify({
        code: code
      })
    })
      .then(console.log)
      .catch(console.log)
  }, [])


  return (
    <div>
      Redirecting...
    </div>
  )
}