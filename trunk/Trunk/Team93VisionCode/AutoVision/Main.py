import cv2
import numpy
import gripfilter
import pubnetwork
cap = cv2.VideoCapture(0)
#create new autovision process
g = gripfilter.Gfilter()
pub = pubnetwork.PublishTables()
while cap.isOpened():



    _,source0 = cv2.VideoCapture.read(cap)

    g.process(source0)
    if len(g.filter_contours_output) > 0:
        # cv2.drawContours(self.resize_cam_source,self.line_contours,-1,(0,255,0),3)
        x,y,w,h = cv2.boundingRect(g.filter_contours_output[0])
        cv2.rectangle(g.resize_image_output, (x, y), (x + w, y + h), (0, 255, 0), 2)
        print "x-center:", (x+(w/2))
        pub.publish('IP', (x+(w/2)))





    else:
        print "No Contours found"
    cv2.imshow("image", g.resize_image_output)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()

