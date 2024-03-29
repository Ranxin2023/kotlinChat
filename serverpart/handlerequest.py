import datetime
import json

from werkzeug.wrappers import Request, Response

from session import Session


class App:
    def __init__(self) -> None:
        pass

    def init_request(self, request):
        resp = None
        if request.method == "POST":
            resp = self.handle_post(request=request)
        else:
            resp = Response("Method Not Allowed", status=405)
        return resp

    def handle_post(self, request):
        sid = request.cookies.get("sid")
        request_body = request.get_json()
        resp = None
        method = request_body["method"]
        args = request_body["args"]
        nickname = str(args[0])
        photo_code = str(args[2])
        # print(f"nick name is{args}")
        if method == "login":
            # store into session
            request.session = Session(sid)
            response_from_db = request.session.store_session_info(args=args)
            # This should store all the session info into the profile of the client
            response_data = json.dumps(
                {
                    "success": response_from_db[0],
                    "error msg": response_from_db[1],
                    "nickname": nickname,
                    "profile photo": photo_code,
                }
            )
            # print(len(request.session.sid))
            resp = Response(response_data, content_type="application/json", status=200)
        if method == "logout":
            request.session = Session(sid)
            request.session.sid = ""

        if request.session:
            expires = datetime.datetime.now() + datetime.timedelta(days=365)
            resp.set_cookie("sid", request.session.sid, expires=expires)
        return resp

    def wsgi_app(self, environ, start_response):
        """WSGI application that processes requests and returns responses."""
        request = Request(environ)
        response = self.init_request(request)
        return response(environ, start_response)

    def __call__(self, environ, start_response):
        """The WSGI server calls this method as the WSGI application."""
        return self.wsgi_app(environ, start_response)


if __name__ == "__main__":
    from werkzeug.serving import run_simple

    run_simple("192.168.108.1", 8080, App())
