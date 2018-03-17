package desmoj.demo.burchardkai;

import desmoj.core.dist.*;
import desmoj.core.simulator.*;
import desmoj.core.statistic.*;
import desmoj.core.util.AccessPoint;
import desmoj.core.util.Parameterizable;
import desmoj.extensions.applicationDomains.harbour.*;
import desmoj.extensions.experimentation.reflect.MutableFieldAccessPoint;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

// Referenced classes of package BurchardkaiTerminal:
//            VanCarrier, TruckArrival, BurchardkaiTerminalRunner

public class BurchardkaiTerminalModel extends Model implements Parameterizable
{

    public BurchardkaiTerminalModel()
    {
        this(null, "BurchardkaiTerminal", 13);
    }

    public BurchardkaiTerminalModel(Model owner, String name)
    {
        this(owner, name, 13);
    }

    public BurchardkaiTerminalModel(Model owner, String name, int nVC)
    {
        super(owner, name, true, true);
        nLanes = 25;
        roadToHO = 1000D;
        pickUpTime = 10D;
        pickDownTime = 10D;
        exportCon = 0.55000000000000004D;
        meanTruckArrivalTime = 40D;
        meanLoadTime1 = 20D;
        stdDevLoadTime1 = 10D;
        meanLoadTime2 = 40D;
        stdDevLoadTime2 = 20D;
        meanLoadTime3 = 60D;
        stdDevLoadTime3 = 30D;
        meanLoadTime4 = 80D;
        stdDevLoadTime4 = 40D;
        meanLoadTime5 = 160D;
        stdDevLoadTime5 = 80D;
        observersOn = true;
        if(nVC <= 0)
        {
            sendWarning("The given number of VCs  is negative or zero. The number of VCs will be set to 13!", String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(getClass().getName())))).append(": ").append(getQuotedName()).append("BurchardkaiTerminalModel, Constructor: (Model owner, String name, ").append("int nVC)"))), "Tne negative number or zero of VCs  does not make sense.", "Make sure to provide a valid positive number of VCs for the BurchardkaiTerminalModel to be constructed.");
            this.nVC = 13;
        } else
        {
            this.nVC = nVC;
        }
    }

    public void incArrivedTrucks()
    {
        trucksArrived.update(++arrivedTrucks);
    }

    public void incFinishedTrucks()
    {
        trucksServiced.update(++finishedTrucks);
    }

    public void updateHOQueue(long mLength, long sLength)
    {
        vcidle.update(mLength);
        hoQueueSLength.update(sLength);
    }

    public void doInitialSchedules()
    {
        tControl = new T_Control(this, "T_Control", 0, nVC, 0, 0, fifo, true, true);
        tControl.debugOn();
        for(int j = 0; j < nVC; j++)
        {
            double speedVC = speedRoad.sample();
            VanCarrier v = new VanCarrier(this, "VC", 1, speedVC, speedVC, speedYard.sample(), tControl, HO, d_matrix, true);
            v.setLocation(HO);
            v.debugOn();
            v.activate();
        }

        TruckArrival ta = new TruckArrival(this, "TruckArrivalEvent", TruckArrivalStream, ExportContainer, true);
        ta.schedule(new TimeInstant(0.0D));
    }

    public void init()
    {
        trucksArrived = new TimeSeries(this, "arrived", new TimeInstant(1.0), new TimeInstant(0.0), false, false);
        trucksServiced = new TimeSeries(this, "finished", new TimeInstant(1.0), new TimeInstant(0.0), false, false);
        vcidle = new TimeSeries(this, "mLength", new TimeInstant(1.0), new TimeInstant(0.0), false, false);
        hoQueueSLength = new TimeSeries(this, "sLength", new TimeInstant(1.0), new TimeInstant(0.0), false, false);
        TruckArrivalStream = new ContDistExponential(this, "TruckArrival", meanTruckArrivalTime, true, true);
        TruckArrivalStream.reset(BurchardkaiTerminalRunner.truckArrivalSeed);
        loadTime1 = new ContDistNormal(this, "VCLoadTime1", meanLoadTime1, stdDevLoadTime1, true, true);
        loadTime1.setNonNegative(true);
        loadTime2 = new ContDistNormal(this, "VCLoadTime2", meanLoadTime2, stdDevLoadTime2, true, true);
        loadTime2.setNonNegative(true);
        loadTime3 = new ContDistNormal(this, "VCLoadTime3", meanLoadTime3, stdDevLoadTime3, true, true);
        loadTime3.setNonNegative(true);
        loadTime4 = new ContDistNormal(this, "VCLoadTime4", meanLoadTime4, stdDevLoadTime4, true, true);
        loadTime4.setNonNegative(true);
        loadTime5 = new ContDistNormal(this, "VCLoadTime5", meanLoadTime5, stdDevLoadTime5, true, true);
        loadTime5.setNonNegative(true);
        loadTruckTime = new ContDistConstant(this, "TruckLoadTime", pickUpTime, true, true);
        loadTruckTime.reset(BurchardkaiTerminalRunner.truckLoadTimeSeed);
        unloadTruckTime = new ContDistConstant(this, "TruckUnloadTime", pickDownTime, true, true);
        truckSpeed = new ContDistNormal(this, "TruckSpeed", 5.5599999999999996D, 0.27799999999999997D, true, true);
        truckSpeed.setNonNegative(true);
        truckSpeed.reset(BurchardkaiTerminalRunner.truckSpeedSeed);
        speedRoad = new ContDistNormal(this, "SpeedRoad", 6.1100000000000003D, 0.30550000000000005D, true, true);
        speedYard = new ContDistNormal(this, "SpeedYard", 3.3300000000000001D, 0.16650000000000001D, true, true);
        loading = new Loading(this, "Loading", loadTruckTime, true);
        unloading = new Unloading(this, "Unloading", unloadTruckTime, true);
        condMyTruck = new MyTruck(this, "MyTruck", null, false);
        ExportContainer = new BoolDistBernoulli(this, "ExportContainer", exportCon, true, true);
        ExportReeferContainer = new BoolDistBernoulli(this, "ExportReeferContainer", 0.029999999999999999D, true, true);
        ImportReeferContainer = new BoolDistBernoulli(this, "ImportReeferContainer", 0.050000000000000003D, true, true);
        HO = new HoldingArea(this, "HoldingArea", 0, nVC, 0, 0, nLanes, true, true);
        HO.debugOn();
        fifo = new FIFO_Transport_Strategy(this);
        ys1 = new MostFreePlaceBlockYardStrategy(this);
        ys2 = new RandomBlockYardStrategy(this);
        yard = new Yard(this, "Yard", null, true, false);
        for(int i = 0; i < c.length; i++)
        {
            Double cap = new Double(0.69999999999999996D * c[i]);
            Double cap1 = new Double(0.5D * c[i]);
            Block block;
            if(i < 22)
            {
                if(i == 10 || i == 21)
                    block = new Block(this, "Block#".concat(String.valueOf(String.valueOf(i))), 0, c[i], cap1.longValue(), 2, false, true);
                else
                    block = new Block(this, "Block#".concat(String.valueOf(String.valueOf(i))), 0, c[i], cap.longValue(), 1, false, true);
            } else
            if(i == 31 || i == 40)
                block = new Block(this, "Block#".concat(String.valueOf(String.valueOf(i))), 1, c[i], cap1.longValue(), 2, false, true);
            else
                block = new Block(this, "Block#".concat(String.valueOf(String.valueOf(i))), 1, c[i], cap.longValue(), 1, false, true);
            yard.addBlock(block);
            block.debugOn();
        }

        MatrixReader m = new MatrixReader(new StringReader("0 92 227 314 423 467 541 633 729 824 952 144 177 245 403 426 487 575 671 767 843 955 261 258 330 420 541 623 714 811 906 1008 316 335 416 503 714 804 900 999 1017 520\n92 0 82 228 321 381 455 547 643 738 866 138 130 159 317 340 401 489 585 681 757 869 255 252 324 334 455 537 628 725 820 922 331 329 410 417 628 718 814 913 931 434\n227 82 0 147 181 252 374 466 562 657 785 214 130 159 236 259 320 408 504 600 676 788 308 305 250 253 374 456 547 644 739 841 389 382 330 336 547 637 733 832 850 353\n314 228 147 0 139 183 257 349 445 540 668 301 217 149 119 142 203 291 387 483 559 671 395 301 229 136 257 339 430 527 622 724 476 389 309 314 430 520 616 715 733 236\n423 321 181 139 0 70 200 292 388 483 611 410 326 258 132 121 146 234 330 426 502 614 504 410 338 245 270 282 373 470 565 667 585 498 418 327 388 463 559 658 676 249\n467 381 252 183 70 0 83 222 318 413 541 454 370 302 176 121 121 164 260 356 432 544 548 454 382 289 268 246 303 400 495 597 629 542 462 371 352 393 489 588 606 293\n541 455 374 257 200 83 0 96 235 330 458 528 444 376 250 195 134 138 177 273 349 461 622 528 456 363 281 259 258 317 412 514 703 616 536 445 347 348 406 505 523 329\n633 547 466 349 292 222 96 0 96 234 362 620 536 468 342 287 226 138 138 177 253 365 682 587 515 422 338 259 258 259 316 418 763 675 595 504 347 348 348 409 427 386\n729 643 562 445 388 318 235 96 0 94 276 716 632 564 438 383 322 234 138 137 176 288 778 683 611 518 434 316 260 258 257 341 859 771 691 600 404 348 346 350 368 482\n824 738 657 540 483 413 330 234 94 0 186 811 727 659 533 478 417 329 233 137 175 193 873 778 706 613 529 411 316 258 257 246 954 866 786 695 499 404 346 329 302 577\n952 866 785 668 611 541 458 362 276 186 0 939 855 787 661 606 545 457 361 265 189 117 1001 906 834 741 657 539 444 349 254 195 1082 994 914 823 585 490 393 319 292 705\n144 138 214 301 410 454 528 620 716 811 939 0 164 232 390 413 474 562 657 754 830 942 82 177 249 344 465 562 657 754 849 951 237 254 335 425 638 735 831 930 948 444\n177 130 130 217 326 370 444 536 632 727 855 164 0 148 306 329 390 467 562 659 746 849 177 82 154 249 370 467 562 659 754 856 258 251 326 332 543 640 736 835 853 349\n245 159 159 149 258 302 376 468 564 659 787 232 148 0 238 261 322 395 490 587 678 777 249 154 82 177 298 395 490 587 682 784 330 323 254 260 471 568 664 763 781 277\n403 317 236 119 132 176 250 342 438 533 661 390 306 238 0 135 195 267 362 459 552 649 405 310 238 145 170 267 362 459 554 656 486 398 318 227 343 440 536 635 653 149\n426 340 259 142 121 121 195 287 383 478 606 413 329 261 135 0 141 214 309 406 497 596 430 335 263 170 195 214 309 406 501 603 511 423 343 252 290 387 483 582 600 174\n487 401 320 203 146 121 134 226 322 417 545 474 390 322 195 141 0 154 249 346 436 536 490 395 323 230 146 154 249 346 441 543 571 483 403 312 230 327 423 522 540 194\n575 489 408 291 234 164 138 138 234 329 457 562 467 395 267 214 154 0 176 272 348 460 562 467 395 302 203 82 177 274 369 471 643 555 475 372 266 267 363 462 480 251\n671 585 504 387 330 260 177 138 138 233 361 657 562 490 362 309 249 176 0 176 252 364 657 562 490 397 298 177 82 179 274 376 738 650 570 467 265 266 268 367 385 346\n767 681 600 483 426 356 273 177 137 137 265 754 659 587 459 406 346 272 176 0 156 268 754 659 587 494 395 274 179 82 177 279 835 747 667 564 362 267 266 270 288 433\n843 757 676 559 502 432 349 253 176 175 189 830 746 678 552 497 436 348 252 156 0 177 849 754 682 589 490 369 274 177 82 184 930 842 762 659 457 362 265 255 228 538\n955 869 788 671 614 544 461 365 288 193 117 942 849 777 649 596 536 460 364 268 177 0 944 849 777 684 585 464 369 272 177 118 1025 937 857 754 508 413 316 242 215 633\n261 255 308 395 504 548 622 682 778 873 1001 82 177 249 405 430 490 562 657 754 849 944 0 173 246 339 462 562 657 754 849 951 176 171 252 342 632 693 781 876 915 415\n258 252 305 301 410 454 528 587 683 778 906 177 82 154 310 335 395 467 562 659 754 849 173 0 153 246 369 467 562 659 754 856 175 159 159 249 539 600 688 783 822 322\n330 324 250 229 338 382 456 515 611 706 834 249 154 82 238 263 323 395 490 587 682 777 246 153 0 173 296 395 490 587 682 784 248 160 160 176 466 527 615 710 749 249\n420 334 253 136 245 289 363 422 518 613 741 344 249 177 145 170 230 302 397 494 589 684 339 246 173 0 203 302 397 494 589 691 341 253 173 176 373 434 522 617 656 156\n541 455 374 257 270 268 281 338 434 529 657 465 370 298 170 195 146 203 298 395 490 585 462 369 296 203 0 203 298 395 490 588 464 376 296 203 230 327 423 522 540 125\n623 537 456 339 282 246 259 259 316 411 539 562 467 395 267 214 154 82 177 274 369 464 562 467 395 302 203 0 175 272 367 446 626 538 458 346 88 185 281 380 398 225\n714 628 547 430 373 303 258 528 260 316 444 657 562 490 362 309 249 177 82 179 274 369 657 562 490 397 298 175 0 177 272 351 721 633 553 441 183 184 186 285 303 320\n811 725 644 527 470 400 317 259 258 258 349 754 659 587 459 406 346 274 179 82 177 272 754 659 587 494 395 272 177 0 175 254 818 730 650 538 280 185 184 188 206 417\n906 820 739 622 565 495 412 316 257 257 254 849 754 682 554 501 441 369 274 177 82 177 849 754 682 589 490 367 272 175 0 159 913 825 745 633 375 280 183 138 111 512\n1008 922 841 724 667 597 514 418 341 246 195 951 856 784 656 603 543 471 376 279 184 118 951 856 784 691 588 446 351 254 159 0 967 879 799 690 454 359 262 160 133 591\n316 331 389 476 585 629 703 763 859 954 1082 237 258 330 486 511 571 643 738 835 930 1025 176 175 248 341 464 626 721 818 913 967 0 87 220 309 592 652 740 835 874 417\n335 329 382 389 498 542 616 675 771 866 994 254 251 323 398 423 483 555 650 747 842 937 171 159 160 253 376 538 633 730 825 879 87 0 80 221 504 564 652 747 786 329\n416 410 330 309 418 462 536 595 691 786 914 335 326 254 318 343 403 475 570 667 762 857 252 159 160 173 296 458 553 650 745 799 220 80 0 90 424 484 572 667 706 249\n503 417 336 314 327 371 445 504 600 695 823 425 332 260 227 252 312 372 467 564 659 754 342 249 176 176 203 346 441 538 633 690 309 221 90 0 315 375 463 558 597 137\n714 628 547 430 388 352 347 347 404 499 585 638 543 471 343 290 230 266 265 362 457 508 632 539 466 373 230 88 183 280 375 454 592 504 424 315 0 154 242 337 376 233\n804 718 637 520 463 393 348 348 348 404 490 735 640 568 440 387 327 267 266 267 362 413 693 600 527 434 327 185 184 185 280 359 652 564 484 375 154 0 96 249 288 330\n900 814 733 616 559 489 406 348 346 346 393 831 736 664 536 483 423 363 268 266 265 316 781 688 615 522 423 281 186 184 183 262 740 652 572 463 242 96 0 98 200 426\n999 913 832 715 658 588 505 409 350 329 319 930 835 763 635 582 522 462 367 270 255 242 876 783 710 617 522 380 285 188 138 160 835 747 667 558 337 249 98 0 67 525\n1017 931 850 733 676 606 523 427 368 302 292 948 853 781 653 600 540 480 385 288 228 215 915 822 749 656 540 398 303 206 111 133 874 786 706 597 376 288 200 67 0 543\n520 434 353 236 249 293 329 386 482 577 705 444 349 277 149 174 194 251 346 443 538 633 415 322 249 156 125 225 320 417 512 591 417 329 249 137 233 330 426 525 543 0"));
        d_matrix = m.getMatrix();
        turnAroundTime = new Tally(this, "TruckTurnAroundTime", true, false);
    }

    public String description()
    {
        return "The BurchardkaiTerminalModel is a process oriented model of the Hamburg Burchardkai Terminal where the trucks arrive and are unloaded or loaded by the VCs using components of Eugenia Neufeld. For detailed information please turn to her diploma thesis paper. <br> Simulated time units are seconds. <br>";
    }

    public static void main(String args[])
    {
        Experiment btExperiment = new Experiment("BurchardkaiTerminal");
        btExperiment.setShowProgressBarAutoclose(true);
        BurchardkaiTerminalModel btModel = new BurchardkaiTerminalModel(null, "Burchardkai Terminal Model", 13);
        btModel.connectToExperiment(btExperiment);
        btExperiment.stop(new TimeInstant(86400D));
        btExperiment.tracePeriod(new TimeInstant(0.0D), new TimeInstant(3600D));
        btExperiment.debugPeriod(new TimeInstant(0.0D), new TimeInstant(3600D));
        btExperiment.start();
        btExperiment.report();
        btExperiment.finish();
    }
    
    /** 
     * Returns the model parameters:
     * vcNumber     : Number of VCs working in the yard.
     */
    public Map<String, AccessPoint> createParameters() {
        Map<String, AccessPoint> pm = new TreeMap<String, AccessPoint>();
        pm.put("nVC", new MutableFieldAccessPoint("nVC", this));
        pm.put("nLanes", new MutableFieldAccessPoint("nLanes", this));
        pm.put("roadToHO", new MutableFieldAccessPoint("roadToHO", this));
        pm.put("pickUpTime", new MutableFieldAccessPoint("pickUpTime", this));
        pm.put("pickDownTime", new MutableFieldAccessPoint("pickDownTime", this));
        pm.put("exportCon", new MutableFieldAccessPoint("exportCon", this));
        pm.put("meanTruckArrivalTime", new MutableFieldAccessPoint("meanTruckArrivalTime", this));
        pm.put("meanLoadTime1", new MutableFieldAccessPoint("meanLoadTime1", this));
        pm.put("meanLoadTime2", new MutableFieldAccessPoint("meanLoadTime2", this));
        pm.put("meanLoadTime3", new MutableFieldAccessPoint("meanLoadTime3", this));
        pm.put("meanLoadTime4", new MutableFieldAccessPoint("meanLoadTime4", this));
        pm.put("meanLoadTime5", new MutableFieldAccessPoint("meanLoadTime5", this));
        pm.put("stdDevLoadTime1", new MutableFieldAccessPoint("stdDevLoadTime1", this));
        pm.put("stdDevLoadTime2", new MutableFieldAccessPoint("stdDevLoadTime2", this));
        pm.put("stdDevLoadTime3", new MutableFieldAccessPoint("stdDevLoadTime3", this));
        pm.put("stdDevLoadTime4", new MutableFieldAccessPoint("stdDevLoadTime4", this));
        pm.put("stdDevLoadTime5", new MutableFieldAccessPoint("stdDevLoadTime5", this));
        return pm;
    }

    protected int nVC;
    protected int nLanes;
    protected HoldingArea HO;
    protected T_Control tControl;
    protected double roadToHO;
    protected double pickUpTime;
    protected double pickDownTime;
    protected MyTruck condMyTruck;
    protected double d_matrix[][];
    protected double exportCon;
    protected long c[] = {
        576L, 480L, 480L, 327L, 384L, 384L, 576L, 576L, 576L, 576L, 
        192L, 600L, 540L, 540L, 360L, 450L, 450L, 570L, 570L, 570L, 
        570L, 120L, 600L, 540L, 540L, 570L, 342L, 570L, 600L, 600L, 
        570L, 192L, 612L, 510L, 510L, 612L, 540L, 720L, 720L, 396L, 
        108L
    };
    protected static ContDistNormal speedRoad;
    protected static ContDistNormal speedYard;
    protected double meanTruckArrivalTime;
    protected static ContDistNormal truckSpeed;
    protected static Tally turnAroundTime;
    protected double meanLoadTime1;
    protected double stdDevLoadTime1;
    protected double meanLoadTime2;
    protected double stdDevLoadTime2;
    protected double meanLoadTime3;
    protected double stdDevLoadTime3;
    protected double meanLoadTime4;
    protected double stdDevLoadTime4;
    protected double meanLoadTime5;
    protected double stdDevLoadTime5;
    protected static ContDistExponential TruckArrivalStream;
    protected static ContDistNormal loadTime1;
    protected static ContDistNormal loadTime2;
    protected static ContDistNormal loadTime3;
    protected static ContDistNormal loadTime4;
    protected static ContDistNormal loadTime5;
    protected static ContDistConstant loadTruckTime;
    protected static ContDistConstant unloadTruckTime;
    protected Loading loading;
    protected Unloading unloading;
    protected static BoolDistBernoulli ExportReeferContainer;
    protected static BoolDistBernoulli ImportReeferContainer;
    protected static BoolDistBernoulli ExportContainer;
    protected static BoolDistBernoulli ImportYardAsDestinationForImportContainer;
    protected FIFO_Transport_Strategy fifo;
    protected MostFreePlaceBlockYardStrategy ys1;
    protected RandomBlockYardStrategy ys2;
    protected Yard yard;
    protected static final String matrix = "0 92 227 314 423 467 541 633 729 824 952 144 177 245 403 426 487 575 671 767 843 955 261 258 330 420 541 623 714 811 906 1008 316 335 416 503 714 804 900 999 1017 520\n92 0 82 228 321 381 455 547 643 738 866 138 130 159 317 340 401 489 585 681 757 869 255 252 324 334 455 537 628 725 820 922 331 329 410 417 628 718 814 913 931 434\n227 82 0 147 181 252 374 466 562 657 785 214 130 159 236 259 320 408 504 600 676 788 308 305 250 253 374 456 547 644 739 841 389 382 330 336 547 637 733 832 850 353\n314 228 147 0 139 183 257 349 445 540 668 301 217 149 119 142 203 291 387 483 559 671 395 301 229 136 257 339 430 527 622 724 476 389 309 314 430 520 616 715 733 236\n423 321 181 139 0 70 200 292 388 483 611 410 326 258 132 121 146 234 330 426 502 614 504 410 338 245 270 282 373 470 565 667 585 498 418 327 388 463 559 658 676 249\n467 381 252 183 70 0 83 222 318 413 541 454 370 302 176 121 121 164 260 356 432 544 548 454 382 289 268 246 303 400 495 597 629 542 462 371 352 393 489 588 606 293\n541 455 374 257 200 83 0 96 235 330 458 528 444 376 250 195 134 138 177 273 349 461 622 528 456 363 281 259 258 317 412 514 703 616 536 445 347 348 406 505 523 329\n633 547 466 349 292 222 96 0 96 234 362 620 536 468 342 287 226 138 138 177 253 365 682 587 515 422 338 259 258 259 316 418 763 675 595 504 347 348 348 409 427 386\n729 643 562 445 388 318 235 96 0 94 276 716 632 564 438 383 322 234 138 137 176 288 778 683 611 518 434 316 260 258 257 341 859 771 691 600 404 348 346 350 368 482\n824 738 657 540 483 413 330 234 94 0 186 811 727 659 533 478 417 329 233 137 175 193 873 778 706 613 529 411 316 258 257 246 954 866 786 695 499 404 346 329 302 577\n952 866 785 668 611 541 458 362 276 186 0 939 855 787 661 606 545 457 361 265 189 117 1001 906 834 741 657 539 444 349 254 195 1082 994 914 823 585 490 393 319 292 705\n144 138 214 301 410 454 528 620 716 811 939 0 164 232 390 413 474 562 657 754 830 942 82 177 249 344 465 562 657 754 849 951 237 254 335 425 638 735 831 930 948 444\n177 130 130 217 326 370 444 536 632 727 855 164 0 148 306 329 390 467 562 659 746 849 177 82 154 249 370 467 562 659 754 856 258 251 326 332 543 640 736 835 853 349\n245 159 159 149 258 302 376 468 564 659 787 232 148 0 238 261 322 395 490 587 678 777 249 154 82 177 298 395 490 587 682 784 330 323 254 260 471 568 664 763 781 277\n403 317 236 119 132 176 250 342 438 533 661 390 306 238 0 135 195 267 362 459 552 649 405 310 238 145 170 267 362 459 554 656 486 398 318 227 343 440 536 635 653 149\n426 340 259 142 121 121 195 287 383 478 606 413 329 261 135 0 141 214 309 406 497 596 430 335 263 170 195 214 309 406 501 603 511 423 343 252 290 387 483 582 600 174\n487 401 320 203 146 121 134 226 322 417 545 474 390 322 195 141 0 154 249 346 436 536 490 395 323 230 146 154 249 346 441 543 571 483 403 312 230 327 423 522 540 194\n575 489 408 291 234 164 138 138 234 329 457 562 467 395 267 214 154 0 176 272 348 460 562 467 395 302 203 82 177 274 369 471 643 555 475 372 266 267 363 462 480 251\n671 585 504 387 330 260 177 138 138 233 361 657 562 490 362 309 249 176 0 176 252 364 657 562 490 397 298 177 82 179 274 376 738 650 570 467 265 266 268 367 385 346\n767 681 600 483 426 356 273 177 137 137 265 754 659 587 459 406 346 272 176 0 156 268 754 659 587 494 395 274 179 82 177 279 835 747 667 564 362 267 266 270 288 433\n843 757 676 559 502 432 349 253 176 175 189 830 746 678 552 497 436 348 252 156 0 177 849 754 682 589 490 369 274 177 82 184 930 842 762 659 457 362 265 255 228 538\n955 869 788 671 614 544 461 365 288 193 117 942 849 777 649 596 536 460 364 268 177 0 944 849 777 684 585 464 369 272 177 118 1025 937 857 754 508 413 316 242 215 633\n261 255 308 395 504 548 622 682 778 873 1001 82 177 249 405 430 490 562 657 754 849 944 0 173 246 339 462 562 657 754 849 951 176 171 252 342 632 693 781 876 915 415\n258 252 305 301 410 454 528 587 683 778 906 177 82 154 310 335 395 467 562 659 754 849 173 0 153 246 369 467 562 659 754 856 175 159 159 249 539 600 688 783 822 322\n330 324 250 229 338 382 456 515 611 706 834 249 154 82 238 263 323 395 490 587 682 777 246 153 0 173 296 395 490 587 682 784 248 160 160 176 466 527 615 710 749 249\n420 334 253 136 245 289 363 422 518 613 741 344 249 177 145 170 230 302 397 494 589 684 339 246 173 0 203 302 397 494 589 691 341 253 173 176 373 434 522 617 656 156\n541 455 374 257 270 268 281 338 434 529 657 465 370 298 170 195 146 203 298 395 490 585 462 369 296 203 0 203 298 395 490 588 464 376 296 203 230 327 423 522 540 125\n623 537 456 339 282 246 259 259 316 411 539 562 467 395 267 214 154 82 177 274 369 464 562 467 395 302 203 0 175 272 367 446 626 538 458 346 88 185 281 380 398 225\n714 628 547 430 373 303 258 528 260 316 444 657 562 490 362 309 249 177 82 179 274 369 657 562 490 397 298 175 0 177 272 351 721 633 553 441 183 184 186 285 303 320\n811 725 644 527 470 400 317 259 258 258 349 754 659 587 459 406 346 274 179 82 177 272 754 659 587 494 395 272 177 0 175 254 818 730 650 538 280 185 184 188 206 417\n906 820 739 622 565 495 412 316 257 257 254 849 754 682 554 501 441 369 274 177 82 177 849 754 682 589 490 367 272 175 0 159 913 825 745 633 375 280 183 138 111 512\n1008 922 841 724 667 597 514 418 341 246 195 951 856 784 656 603 543 471 376 279 184 118 951 856 784 691 588 446 351 254 159 0 967 879 799 690 454 359 262 160 133 591\n316 331 389 476 585 629 703 763 859 954 1082 237 258 330 486 511 571 643 738 835 930 1025 176 175 248 341 464 626 721 818 913 967 0 87 220 309 592 652 740 835 874 417\n335 329 382 389 498 542 616 675 771 866 994 254 251 323 398 423 483 555 650 747 842 937 171 159 160 253 376 538 633 730 825 879 87 0 80 221 504 564 652 747 786 329\n416 410 330 309 418 462 536 595 691 786 914 335 326 254 318 343 403 475 570 667 762 857 252 159 160 173 296 458 553 650 745 799 220 80 0 90 424 484 572 667 706 249\n503 417 336 314 327 371 445 504 600 695 823 425 332 260 227 252 312 372 467 564 659 754 342 249 176 176 203 346 441 538 633 690 309 221 90 0 315 375 463 558 597 137\n714 628 547 430 388 352 347 347 404 499 585 638 543 471 343 290 230 266 265 362 457 508 632 539 466 373 230 88 183 280 375 454 592 504 424 315 0 154 242 337 376 233\n804 718 637 520 463 393 348 348 348 404 490 735 640 568 440 387 327 267 266 267 362 413 693 600 527 434 327 185 184 185 280 359 652 564 484 375 154 0 96 249 288 330\n900 814 733 616 559 489 406 348 346 346 393 831 736 664 536 483 423 363 268 266 265 316 781 688 615 522 423 281 186 184 183 262 740 652 572 463 242 96 0 98 200 426\n999 913 832 715 658 588 505 409 350 329 319 930 835 763 635 582 522 462 367 270 255 242 876 783 710 617 522 380 285 188 138 160 835 747 667 558 337 249 98 0 67 525\n1017 931 850 733 676 606 523 427 368 302 292 948 853 781 653 600 540 480 385 288 228 215 915 822 749 656 540 398 303 206 111 133 874 786 706 597 376 288 200 67 0 543\n520 434 353 236 249 293 329 386 482 577 705 444 349 277 149 174 194 251 346 443 538 633 415 322 249 156 125 225 320 417 512 591 417 329 249 137 233 330 426 525 543 0";
    private int arrivedTrucks;
    private int finishedTrucks;
    protected TimeSeries trucksArrived;
    protected TimeSeries trucksServiced;
    protected TimeSeries vcidle;
    protected TimeSeries hoQueueSLength;
    protected boolean observersOn;
}
