 
//**************************************************************************
//* WARNING: This file was automatically generated.  Any changes you make  *
//*          to this file will be lost if you generate the file again.     *
//**************************************************************************
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <nivision.h>
#include <nimachinevision.h>
#include <windows.h>

// If you call Machine Vision functions in your script, add NIMachineVision.c to the project.

#define IVA_MAX_BUFFERS 10
#define IVA_STORE_RESULT_NAMES

#define VisionErrChk(Function) {if (!(Function)) {success = 0; goto Error;}}

typedef enum IVA_ResultType_Enum {IVA_NUMERIC, IVA_BOOLEAN, IVA_STRING} IVA_ResultType;

typedef union IVA_ResultValue_Struct    // A result in Vision Assistant can be of type double, BOOL or string.
{
    double numVal;
    BOOL   boolVal;
    char*  strVal;
} IVA_ResultValue;

typedef struct IVA_Result_Struct
{
#if defined (IVA_STORE_RESULT_NAMES)
    char resultName[256];           // Result name
#endif
    IVA_ResultType  type;           // Result type
    IVA_ResultValue resultVal;      // Result value
} IVA_Result;

typedef struct IVA_StepResultsStruct
{
#if defined (IVA_STORE_RESULT_NAMES)
    char stepName[256];             // Step name
#endif
    int         numResults;         // number of results created by the step
    IVA_Result* results;            // array of results
} IVA_StepResults;

typedef struct IVA_Data_Struct
{
    Image* buffers[IVA_MAX_BUFFERS];            // Vision Assistant Image Buffers
    IVA_StepResults* stepResults;              // Array of step results
    int numSteps;                               // Number of steps allocated in the stepResults array
    CoordinateSystem *baseCoordinateSystems;    // Base Coordinate Systems
    CoordinateSystem *MeasurementSystems;       // Measurement Coordinate Systems
    int numCoordSys;                            // Number of coordinate systems
} IVA_Data;



static IVA_Data* IVA_InitData(int numSteps, int numCoordSys);
static int IVA_DisposeData(IVA_Data* ivaData);
static int IVA_DisposeStepResults(IVA_Data* ivaData, int stepIndex);
static int IVA_FindEdge3(Image* image,
                                  ROI* roi, 
								  int pDirection,
								  int pPolarity,
								  unsigned int pKernelSize,
								  unsigned int pWidth,
								  float pMinThreshold,
								  int pInterpolationType,
								  int pColumnProcessingMode,
							      unsigned int pStepSize,
								  int pSearchMode,
                                  IVA_Data* ivaData,
                                  int stepIndex);

int IVA_ProcessImage(Image *image)
{
	int success = 1;
    IVA_Data *ivaData;
    ROI *roi;

    // Initializes internal data (buffers and array of points for caliper measurements)
    VisionErrChk(ivaData = IVA_InitData(1, 0));

    // Creates a new, empty region of interest.
    VisionErrChk(roi = imaqCreateROI());

    // Creates a new rectangle ROI contour and adds the rectangle to the provided ROI.
    VisionErrChk(imaqAddRotatedRectContour(roi, imaqMakeRotatedRect(5, 2, 237, 318, 0)));

	VisionErrChk(IVA_FindEdge3(image, roi, IMAQ_BOTTOM_TO_TOP, 
		IMAQ_SEARCH_FOR_FALLING_EDGES, 21, 1, 23, IMAQ_BILINEAR_FIXED, 
		IMAQ_AVERAGE_COLUMNS, 15, IMAQ_USE_BEST_RAKE_EDGES, ivaData, 0));

    // Cleans up resources associated with the object
    imaqDispose(roi);

    // Releases the memory allocated in the IVA_Data structure.
    IVA_DisposeData(ivaData);

Error:
	return success;
}

////////////////////////////////////////////////////////////////////////////////
//
// Function Name: IVA_FindEdge3
//
// Description  : Locates a straight edge in a rectangular search area.
//
// Parameters   : image                  -  Input image
//                roi                    -  Region of interest
//                pDirection             -
//                pPolarity              -
//                pKernelSize            -
//                pWidth                 -
//                pMinThreshold          -
//                pInterpolationType     -
//                pColumnProcessingMode  -
//                pStepSize              -  Number of pixels that separates two
//                                          consecutive search lines.
//                pSearchMode            -
//                ivaData                -  Internal Data structure
//                stepIndex              -  Step index (index at which to store
//                                          the results in the resuts array)
//
// Return Value : success
//
////////////////////////////////////////////////////////////////////////////////
static int IVA_FindEdge3(Image* image,
                                  ROI* roi,
								  int pDirection,
								  int pPolarity,
								  unsigned int pKernelSize,
								  unsigned int pWidth,
								  float pMinThreshold,
								  int pInterpolationType,
								  int pColumnProcessingMode,
							      unsigned int pStepSize,
								  int pSearchMode,
                                  IVA_Data* ivaData,
                                  int stepIndex)
{
    int success = TRUE;
	EdgeOptions2 edgeOptions;
    FindEdgeOptions2 findEdgeOptions;
	StraightEdgeOptions straightEdgeOptions;
    FindEdgeReport* findEdgeReport = NULL;
    IVA_Result* edgeResults;
    unsigned int visionInfo;
    
    
    //-------------------------------------------------------------------//
    //                         Find Straight Edge                        //
    //-------------------------------------------------------------------//

	edgeOptions.polarity = pPolarity;
	edgeOptions.kernelSize = pKernelSize;
	edgeOptions.width = pWidth;
	edgeOptions.minThreshold = pMinThreshold;
	edgeOptions.interpolationType = pInterpolationType;
	edgeOptions.columnProcessingMode = pColumnProcessingMode;
	
	findEdgeOptions.direction = pDirection;
	findEdgeOptions.showSearchArea = TRUE;
	findEdgeOptions.showSearchLines = TRUE;
	findEdgeOptions.showEdgesFound = TRUE;
	findEdgeOptions.showResult = TRUE;
	findEdgeOptions.searchAreaColor = IMAQ_RGB_GREEN;
	findEdgeOptions.searchLinesColor = IMAQ_RGB_BLUE;
	findEdgeOptions.searchEdgesColor = IMAQ_RGB_YELLOW;
	findEdgeOptions.resultColor = IMAQ_RGB_RED;
	findEdgeOptions.overlayGroupName = NULL;
	findEdgeOptions.edgeOptions = edgeOptions;
	
	straightEdgeOptions.numLines = 1;
	straightEdgeOptions.searchMode = pSearchMode;
	straightEdgeOptions.minScore = 10;
	straightEdgeOptions.maxScore = 1000;
	straightEdgeOptions.orientation = 0;
	straightEdgeOptions.angleRange = 45;
	straightEdgeOptions.angleTolerance = 1;
	straightEdgeOptions.stepSize = pStepSize;
	straightEdgeOptions.minSignalToNoiseRatio = 0;
	straightEdgeOptions.minCoverage = 25;
	straightEdgeOptions.houghIterations = 5;
	
    // Locates a straight edge in the rectangular search area.
    VisionErrChk(findEdgeReport = imaqFindEdge2(image, roi, NULL, NULL, &findEdgeOptions, &straightEdgeOptions));

    // ////////////////////////////////////////
    // Store the results in the data structure.
    // ////////////////////////////////////////
    
    // First, delete all the results of this step (from a previous iteration)
    IVA_DisposeStepResults(ivaData, stepIndex);

    // Check if the image is calibrated.
    VisionErrChk(imaqGetVisionInfoTypes(image, &visionInfo));

    // If the image is calibrated, we also need to log the calibrated position (x and y) -> 9 results instead of 5
    ivaData->stepResults[stepIndex].numResults = (visionInfo & IMAQ_VISIONINFO_CALIBRATION ? 10 : 5);
    ivaData->stepResults[stepIndex].results = malloc (sizeof(IVA_Result) * ivaData->stepResults[stepIndex].numResults);
    edgeResults = ivaData->stepResults[stepIndex].results;
    
    if (edgeResults && (findEdgeReport->numStraightEdges > 0))
    {
        #if defined (IVA_STORE_RESULT_NAMES)
            sprintf(edgeResults->resultName, "Point 1.X Position (Pix.)");
        #endif
        edgeResults->type = IVA_NUMERIC;
        edgeResults->resultVal.numVal = findEdgeReport->straightEdges->straightEdgeCoordinates.start.x;
        edgeResults++;

        #if defined (IVA_STORE_RESULT_NAMES)
            sprintf(edgeResults->resultName, "Point 1.Y Position (Pix.)");
        #endif
        edgeResults->type = IVA_NUMERIC;
        edgeResults->resultVal.numVal = findEdgeReport->straightEdges->straightEdgeCoordinates.start.y;
        edgeResults++;
        
        #if defined (IVA_STORE_RESULT_NAMES)
            sprintf(edgeResults->resultName, "Point 2.X Position (Pix.)");
        #endif
        edgeResults->type = IVA_NUMERIC;
        edgeResults->resultVal.numVal = findEdgeReport->straightEdges->straightEdgeCoordinates.end.x;
        edgeResults++;

        #if defined (IVA_STORE_RESULT_NAMES)
            sprintf(edgeResults->resultName, "Point 2.Y Position (Pix.)");
        #endif
        edgeResults->type = IVA_NUMERIC;
        edgeResults->resultVal.numVal = findEdgeReport->straightEdges->straightEdgeCoordinates.end.y;
        edgeResults++;
            
        if (visionInfo & IMAQ_VISIONINFO_CALIBRATION)
        {
	        #if defined (IVA_STORE_RESULT_NAMES)
                sprintf(edgeResults->resultName, "Point1.X Position (World)");
            #endif
            edgeResults->type = IVA_NUMERIC;
            edgeResults->resultVal.numVal = findEdgeReport->straightEdges->calibratedStraightEdgeCoordinates.start.x;
            edgeResults++;

            #if defined (IVA_STORE_RESULT_NAMES)
                sprintf(edgeResults->resultName, "point1.Y Position (World)");
            #endif
            edgeResults->type = IVA_NUMERIC;
            edgeResults->resultVal.numVal = findEdgeReport->straightEdges->calibratedStraightEdgeCoordinates.start.y;
            edgeResults++;
		
            #if defined (IVA_STORE_RESULT_NAMES)
                sprintf(edgeResults->resultName, "Point2.X Position (World)");
            #endif
            edgeResults->type = IVA_NUMERIC;
            edgeResults->resultVal.numVal = findEdgeReport->straightEdges->calibratedStraightEdgeCoordinates.end.x;
            edgeResults++;

            #if defined (IVA_STORE_RESULT_NAMES)
                sprintf(edgeResults->resultName, "point2.Y Position (World)");
            #endif
            edgeResults->type = IVA_NUMERIC;
            edgeResults->resultVal.numVal = findEdgeReport->straightEdges->calibratedStraightEdgeCoordinates.end.y;
            edgeResults++;
        }
        
        #if defined (IVA_STORE_RESULT_NAMES)
            sprintf(edgeResults->resultName, "Angle (degrees)");
        #endif
        edgeResults->type = IVA_NUMERIC;
        edgeResults->resultVal.numVal = findEdgeReport->straightEdges->angle;
        edgeResults++;
	
	    if (visionInfo & IMAQ_VISIONINFO_CALIBRATION)
        {
	        #if defined (IVA_STORE_RESULT_NAMES)
                sprintf(edgeResults->resultName, "Angle (World)");
            #endif
            edgeResults->type = IVA_NUMERIC;
            edgeResults->resultVal.numVal = findEdgeReport->straightEdges->calibratedAngle;
            edgeResults++;
	    }
    }
    
Error:
    // Disposes the edge report
    imaqDispose(findEdgeReport);

    return success;
}


////////////////////////////////////////////////////////////////////////////////
//
// Function Name: IVA_InitData
//
// Description  : Initializes data for buffer management and results.
//
// Parameters   : # of steps
//                # of coordinate systems
//
// Return Value : success
//
////////////////////////////////////////////////////////////////////////////////
static IVA_Data* IVA_InitData(int numSteps, int numCoordSys)
{
    int success = 1;
    IVA_Data* ivaData = NULL;
    int i;


    // Allocate the data structure.
    VisionErrChk(ivaData = (IVA_Data*)malloc(sizeof (IVA_Data)));

    // Initializes the image pointers to NULL.
    for (i = 0 ; i < IVA_MAX_BUFFERS ; i++)
        ivaData->buffers[i] = NULL;

    // Initializes the steo results array to numSteps elements.
    ivaData->numSteps = numSteps;

    ivaData->stepResults = (IVA_StepResults*)malloc(ivaData->numSteps * sizeof(IVA_StepResults));
    for (i = 0 ; i < numSteps ; i++)
    {
        #if defined (IVA_STORE_RESULT_NAMES)
            sprintf(ivaData->stepResults[i].stepName, "");
        #endif
        ivaData->stepResults[i].numResults = 0;
        ivaData->stepResults[i].results = NULL;
    }

    // Create the coordinate systems
	ivaData->baseCoordinateSystems = NULL;
	ivaData->MeasurementSystems = NULL;
	if (numCoordSys)
	{
		ivaData->baseCoordinateSystems = (CoordinateSystem*)malloc(sizeof(CoordinateSystem) * numCoordSys);
		ivaData->MeasurementSystems = (CoordinateSystem*)malloc(sizeof(CoordinateSystem) * numCoordSys);
	}

    ivaData->numCoordSys = numCoordSys;

Error:
    return ivaData;
}


////////////////////////////////////////////////////////////////////////////////
//
// Function Name: IVA_DisposeData
//
// Description  : Releases the memory allocated in the IVA_Data structure
//
// Parameters   : ivaData  -  Internal data structure
//
// Return Value : success
//
////////////////////////////////////////////////////////////////////////////////
static int IVA_DisposeData(IVA_Data* ivaData)
{
    int i;


    // Releases the memory allocated for the image buffers.
    for (i = 0 ; i < IVA_MAX_BUFFERS ; i++)
        imaqDispose(ivaData->buffers[i]);

    // Releases the memory allocated for the array of measurements.
    for (i = 0 ; i < ivaData->numSteps ; i++)
        IVA_DisposeStepResults(ivaData, i);

    free(ivaData->stepResults);

    // Dispose of coordinate systems
    if (ivaData->numCoordSys)
    {
        free(ivaData->baseCoordinateSystems);
        free(ivaData->MeasurementSystems);
    }

    free(ivaData);

    return TRUE;
}


////////////////////////////////////////////////////////////////////////////////
//
// Function Name: IVA_DisposeStepResults
//
// Description  : Dispose of the results of a specific step.
//
// Parameters   : ivaData    -  Internal data structure
//                stepIndex  -  step index
//
// Return Value : success
//
////////////////////////////////////////////////////////////////////////////////
static int IVA_DisposeStepResults(IVA_Data* ivaData, int stepIndex)
{
    int i;

    
    for (i = 0 ; i < ivaData->stepResults[stepIndex].numResults ; i++)
    {
        if (ivaData->stepResults[stepIndex].results[i].type == IVA_STRING)
            free(ivaData->stepResults[stepIndex].results[i].resultVal.strVal);
    }

    free(ivaData->stepResults[stepIndex].results);

    return TRUE;
}


