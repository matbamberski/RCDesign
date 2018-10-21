package mainalgorithm;

import java.lang.reflect.Array;
import java.util.ArrayList;

import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import reinforcement.axisload.SymmetricalTensilingBeamReinforcement;

public class KNGAlgorithm extends SymmetricalTensilingBeamReinforcement{
	
	protected Concrete concrete;
	protected Steel steel;
	protected DimensionsOfCrossSectionOfConcrete dimensions;
	protected double s;
	protected double sMin;
	protected double AS1;
	protected double AS2;
	protected double AS1_;
	protected double AS2_;
	
	protected double AsBase;
	
	protected double N0;
	protected double N1_;
	protected double N1;
	protected double N2_;
	protected double N2;
	protected double N3_;
	protected double N3;
	protected double N4_;
	protected double N4;
	protected double N5_;
	protected double N5;
	protected double N6_;
	protected double N6;
	protected double N7;
	
	protected double FYD;
	protected double FCD;
	protected double ES;
	protected double L;
	
	protected int IST;
	
	
	public KNGAlgorithm(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions) {
		super(concrete.getLAMBDA(), concrete.getETA());
		this.concrete = concrete;
		this.steel = steel;
		this.dimensions = dimensions;
	}

	protected void prepareValues() {
		FYD = steel.getFYd()*1000.0;
		FCD = ETA*concrete.getFCd()*1000.0;
		ES = steel.getES() * 1000000.0;
		IST = 1;
		L=LAMBDA;
	}
	
	protected void setSValues() {
		sMin = 0.5*aSMin/aS;/////?????????
		s = sMin;
	}
	
	protected void setAsValues() {
		AS1 = s*aS;
		AS2 = aS - AS1;
		AS1_ = AS2;
		AS2_ = AS1;
	}
	
	public void setAsBase(double AS1, double AS2) {
		AsBase = AS1+AS2;
	}
	
	protected double chooseBiggestN(ArrayList<ForcesCombination> combinations) {
		double Nmax = 0;
		for (ForcesCombination combination: combinations) {
			if (Math.abs(combination.getN())>Nmax) Nmax = combination.getN();
		}
		return Nmax;
	}
	
	protected void setAsMinAndAs(ArrayList<ForcesCombination> combinations) {
		setASMin(steel, chooseBiggestN(combinations), dimensions.getB(), dimensions.getH());
		if (aS<aSMin) aS = aSMin;
	}
	
	protected void countNUltimate() {
		
		// TODO Sprawdzic jednostki - przy ES *1000, czy x s¹ dobrze liczone (podzieliæ przez ES!!!!!!)
		N0 = -FYD*(AS1+AS2);
		N1 = -FYD*AS1-FYD*AS2
				+FCD*dimensions.getB()*L*xMinMinusYd; //XMIN1
		N1_= -FYD*AS1_
				-FYD*AS2_
				+FCD*dimensions.getB()*L*xMinMinusYd;
		N2 = -FYD*AS1
				+FYD*AS2
				+FCD*dimensions.getB()*L*xMinYd;
		N2_= -FYD*AS1_
				+FYD*AS2_
				+FCD*dimensions.getB()*L*xMinYd;
		N3 = -FYD*AS1
				+FYD*AS2
				+FCD*dimensions.getB()*L*xLim;
		N3_= -FYD*AS1_
				+FYD*AS2_
				+FCD*dimensions.getB()*L*xLim;
		N4 = -((concrete.getEpsilonCU3()*(dimensions.getD()-dimensions.getH())/dimensions.getH())*ES)*AS1
				+FYD*AS2
				+FCD*dimensions.getB()*L*dimensions.getH();
		N4_= -((concrete.getEpsilonCU3()*(dimensions.getD()-dimensions.getH())/dimensions.getH())*ES)*AS1_
				+FYD*AS2_
				+FCD*dimensions.getB()*L*dimensions.getH();
		N5 = -((concrete.getEpsilonC3()*(dimensions.getD()-dimensions.getH()/L)/(dimensions.getH()/L-x0))*ES)*AS1
				+FYD*AS2
				+FCD*dimensions.getB()*dimensions.getH();
		N5_= -((concrete.getEpsilonC3()*(dimensions.getD()-dimensions.getH()/L)/(dimensions.getH()/L-x0))*ES)*AS1_
				+FYD*AS2_
				+FCD*dimensions.getB()*dimensions.getH();
		N6 = -((concrete.getEpsilonC3()*(dimensions.getD()-xMaxYd)/(xMaxYd-x0))*ES)*AS1
				+FYD*AS2
				+FCD*dimensions.getB()*dimensions.getH();
		N6_= -((concrete.getEpsilonC3()*(dimensions.getD()-xMaxYd)/(xMaxYd-x0))*ES)*AS1_
				+FYD*AS2_
				+FCD*dimensions.getB()*dimensions.getH();
		N7 = concrete.getEpsilonC3()*ES*(AS1+AS2)
				+FCD*dimensions.getB()*dimensions.getH();
	}
	
	protected double countMrdWithFcd(double AS1, double AS2) {
		return sigmaS1*AS1*(0.5*dimensions.getH()-dimensions.getA1())
		+ sigmaS2*AS2*(0.5*dimensions.getH()-dimensions.getA2())
		+ FCD*dimensions.getB()*L*x*(0.5*dimensions.getH()-0.5*L*x);
	}
	
	protected double countMrdWithoutFcd(double AS1, double AS2) {
		return sigmaS1*AS1*(0.5*dimensions.getH()-dimensions.getA1())
				+ sigmaS2*AS2*(0.5*dimensions.getH()-dimensions.getA2());
	}
	
	protected void prepareXValues() {
		setX0(concrete, steel, dimensions.getH());
		setXLim(concrete, steel, dimensions.getD());
		setXMaxYd(concrete, steel, dimensions.getA2());
		setXMinMinusYd(concrete, steel, dimensions.getA2());
		setXMinYd(concrete, steel, dimensions.getA2());
	}
	
	// PRZYPADEK 0<X<X(-yd,min)
	protected void case1(ForcesCombination combination, double AS1, double AS2) {
		//double NED = combination.getN();
		x = (combination.getN() + FYD*(AS1+AS2))/(FCD*dimensions.getB()*L);
		sigmaS1 = FYD;
		double ss2 = concrete.getEpsilonCU3()*(x-dimensions.getA2())*ES/x;
		sigmaS2 = ss2 <= -FYD ? -FYD : ss2;
	}
	
	protected void case1UpPart(ForcesCombination combination, double AS1, double AS2) {
		case1(combination, AS1, AS2);
		combination.setnRd(combination.getN());
		combination.setmRd(countMrdWithFcd(AS1, AS2));
	}
	
	protected void case1DownPart(ForcesCombination combination, double AS1_, double AS2_) {
		case1(combination, AS1_, AS2_);
		combination.setnRd_(combination.getN());
		combination.setmRd_(-countMrdWithFcd(AS1_, AS2_));
		
	}
	
	//PRZYPADEK X(-yd,min) < X < X(yd,min)
	protected void case2(ForcesCombination combination, double AS1, double AS2) {
		double B = (combination.getN()+AS1*FYD-concrete.getEpsilonCU3()*ES*AS2)/(FCD*dimensions.getB()*L);
		double C = (concrete.getEpsilonCU3()*ES*AS2*dimensions.getA2())/(FCD*dimensions.getB()*L);
		double delta = Math.pow(B, 2) + 4*C;
		x = (B + Math.sqrt(delta))/2;
		sigmaS1 = FYD;
		sigmaS2 = concrete.getEpsilonCU3()*(x-dimensions.getA2())*ES/x;
	}
	
	protected void case2UpPart(ForcesCombination combination, double AS1, double AS2) {
		case2(combination, AS1, AS2);
		combination.setnRd(combination.getN());
		combination.setmRd(countMrdWithFcd(AS1, AS2));
	}
	
	protected void case2DownPart(ForcesCombination combination, double AS1_, double AS2_) {
		case2(combination, AS1_, AS2_);
		combination.setnRd_(combination.getN());
		combination.setmRd_(-countMrdWithFcd(AS1_, AS2_));
	}
	
	//PRZYPADEK X(yd,min) < X < X(lmin)
	protected void case3(ForcesCombination combination, double AS1, double AS2) {
		x = (combination.getN() + FYD*(AS1-AS2))/(FCD*dimensions.getB()*L);
		sigmaS1 = FYD;
		sigmaS2 = FYD;	
	}
	
	protected void case3UpPart(ForcesCombination combination, double AS1, double AS2) {
		case3(combination, AS1, AS2);
		combination.setnRd(combination.getN());
		combination.setmRd(countMrdWithFcd(AS1, AS2));
	}
	
	protected void case3DownPart(ForcesCombination combination, double AS1_, double AS2_) {
		case3(combination, AS1_, AS2_);
		combination.setnRd_(combination.getN());
		combination.setmRd_(-countMrdWithFcd(AS1_, AS2_));
	}
	
	//PRZYPADEK X(lim) < X < h
	protected void case4(ForcesCombination combination, double AS1, double AS2) {
		double B = (combination.getN() - AS2*FYD - concrete.getEpsilonCU3()*ES*AS1)/(FCD*dimensions.getB()*L);
		double C = (concrete.getEpsilonCU3()*ES*AS1*dimensions.getD())/(FCD*dimensions.getB()*L);
		double delta = Math.pow(B, 2) + 4*C;
		x = (B + Math.sqrt(delta))/2;
		sigmaS1 = concrete.getEpsilonCU3()*(dimensions.getD()-x)*ES/x;
		sigmaS2 = FYD;
	}
	
	protected void case4UpPart(ForcesCombination combination, double AS1, double AS2) {
		case4(combination, AS1, AS2);
		combination.setnRd(combination.getN());
		combination.setmRd(countMrdWithFcd(AS1, AS2));
	}
	
	protected void case4DownPart(ForcesCombination combination, double AS1_, double AS2_) {
		case4(combination, AS1_, AS2_);
		combination.setnRd_(combination.getN());
		combination.setmRd_(-countMrdWithFcd(AS1_, AS2_));
	}
	
	//PRZYPADEK h < X < h/L
	protected void case5(ForcesCombination combination, double AS1, double AS2) {
		double B = (combination.getN()-concrete.getEpsilonC3()*AS1 - FYD*AS2 + FCD*dimensions.getB()*L*x0)/(FCD*dimensions.getB()*L);
		double C = (x0*(FYD*AS2-combination.getN())+concrete.getEpsilonC3()*ES*AS1*dimensions.getD())/(FCD*dimensions.getB()*L);
		double delta = Math.pow(B, 2) + 4*C;
		x = (B+ Math.sqrt(delta))/2;
		sigmaS1 = concrete.getEpsilonC3()*(dimensions.getD()-x)*ES/(x-x0);
		sigmaS2 = FYD;
	}
	
	protected void case5UpPart(ForcesCombination combination, double AS1, double AS2) {
		case5(combination, AS1, AS2);
		combination.setnRd(combination.getN());
		combination.setmRd(countMrdWithFcd(AS1, AS2));
	}
	
	protected void case5DownPart(ForcesCombination combination, double AS1_, double AS2_) {
		case5(combination, AS1_, AS2_);
		combination.setnRd_(combination.getN());
		combination.setmRd_(-countMrdWithFcd(AS1_, AS2_));
	}
	
	//PRZYPADEK N5>N7
	protected void case55UpPart(ForcesCombination combination, double AS1, double AS2) {
		case5UpPart(combination, AS1, AS2);
		N5OrN6GreaterThanN7(combination, AS1, AS2);
		combination.setmRd_(countMrdWithoutFcd(AS1, AS2));		
	}
	
	protected void case55DownPart(ForcesCombination combination, double AS1_, double AS2_) {
		case5DownPart(combination, AS1_, AS2_);
		N5OrN6GreaterThanN7(combination, AS1_, AS2_);
		combination.setmRd(-countMrdWithoutFcd(AS1_, AS2_));
	}
	
	//PRZYPADEK h/L < X < x(yd,max)
	protected void case6(ForcesCombination combination, double AS1, double AS2) {
		x = (concrete.getEpsilonC3()*ES*AS1*dimensions.getD()
				+x0*(-combination.getN()+FYD*AS2+FCD*dimensions.getB()*dimensions.getH()))/
				(concrete.getEpsilonC3()*ES*AS1-combination.getN()
						+FYD*AS2+FCD*dimensions.getB()*dimensions.getH());
		sigmaS1 = concrete.getEpsilonC3()*(dimensions.getD()-x)*ES/(x-x0);
		sigmaS2 = FYD;
	}
	
	protected void case6UpPart(ForcesCombination combination, double AS1, double AS2) {
		case6(combination, AS1, AS2);
		combination.setnRd(combination.getN());
		combination.setmRd(countMrdWithoutFcd(AS1, AS2));
	}
	
	protected void case6DownPart(ForcesCombination combination, double AS1_, double AS2_) {
		case6(combination, AS1_, AS2_);
		combination.setnRd_(combination.getN());
		combination.setmRd_(-countMrdWithoutFcd(AS1_, AS2_));
	}
	
	//PRZYPADEK N6>N7
	protected void case66UpPart(ForcesCombination combination, double AS1, double AS2) {
		case6UpPart(combination, AS1, AS2);
		N5OrN6GreaterThanN7(combination, AS1, AS2);
		combination.setmRd_(countMrdWithoutFcd(AS1, AS2));
	}
	
	protected void case66DownPart(ForcesCombination combination, double AS1_, double AS2_) {
		case6DownPart(combination, AS1_, AS2_);
		N5OrN6GreaterThanN7(combination, AS1_, AS2_);
		combination.setmRd(-countMrdWithoutFcd(AS1_, AS2_));
	}
	
	//PRZYPADEK X>x(yd,max)
	protected void case7UpPart(ForcesCombination combination, double AS1, double AS2) {
		N5OrN6GreaterThanN7(combination, AS1, AS2);
		combination.setnRd(combination.getN());
		combination.setmRd(countMrdWithoutFcd(AS1, AS2));
	}
	
	protected void case7DownPart(ForcesCombination combination, double AS1_, double AS2_) {
		N5OrN6GreaterThanN7(combination, AS1_, AS2_);
		combination.setnRd(combination.getN());
		combination.setmRd(-countMrdWithoutFcd(AS1_, AS2_));
	}
	
	protected void N5OrN6GreaterThanN7(ForcesCombination combination, double AS1, double AS2) {
		x = (concrete.getEpsilonC3()*ES*(AS1*dimensions.getD()+AS2*dimensions.getA2())
				+x0*(-combination.getN()+FCD*dimensions.getB()*dimensions.getH()))/
				(concrete.getEpsilonC3()*ES*(AS1+AS2)-combination.getN()
						+FCD*dimensions.getB()*dimensions.getH());
		if (AS1==AS2) x=100000;
		sigmaS1=concrete.getEpsilonC3()*(dimensions.getD()-x)*ES/(x-x0);
		sigmaS2=concrete.getEpsilonC3()*(x-dimensions.getA2())*ES/(x-x0);
	}
	
	protected void findUpParts(ForcesCombination combination) {
		double N = combination.getN();
		if (N>= N0 && N<=N1) case1UpPart(combination, AS1, AS2);
		else if (N>= N1 && N<N2) case2UpPart(combination, AS1, AS2);
		else if (N>= N2 && N<=N3) case3UpPart(combination, AS1, AS2);
		else if (N> N3 && N<=N4) case4UpPart(combination, AS1, AS2);
		else if (N> N4 && N<=N5 && N5>N7) case55UpPart(combination, AS1, AS2);
		else if (N> N4 && N<=N5) case5UpPart(combination, AS1, AS2);
		else if (N>= N5 && N<=N6 && N6>N7) case66UpPart(combination, AS1, AS2);
		else if (N>= N5 && N<=N6) case6UpPart(combination, AS1, AS2);
		else if (N>= N6 && N<=N7) case7UpPart(combination, AS1, AS2);		
		else System.out.println("Blad! Sila nie miesci sie w zakresie od N0 do N7!");
	}

	protected void findDownParts(ForcesCombination combination) {
		double N = combination.getN();
		if (N>= N0 && N<=N1_) case1DownPart(combination, AS1_, AS2_);
		else if (N>= N1_ && N<=N2_) case2DownPart(combination, AS1_, AS2_);
		else if (N>= N2_ && N<=N3_) case3DownPart(combination, AS1_, AS2_);
		else if (N> N3_ && N<=N4_) case4DownPart(combination, AS1_, AS2_);
		else if (N> N4_ && N<=N5_ && N5_>N7) case55DownPart(combination, AS1_, AS2_);
		else if (N> N4_ && N<=N5_) case5DownPart(combination, AS1_, AS2_);
		else if (N>= N5_ && N<=N6_ && N6_>N7) case6DownPart(combination, AS1_, AS2_);
		else if (N>= N5_ && N<=N6_) case66DownPart(combination, AS1_, AS2_);
		else if (N>= N6_ && N<=N7) case7DownPart(combination, AS1_, AS2_);	
		else System.out.println("Blad! Sila nie miesci sie w zakresie od N0 do N7!");		
	}
	
	protected boolean checkNEdLessN0OrGreaterThanNMax(ArrayList<ForcesCombination> combinations) {
		double Nmax = Math.max(Math.max(N5, N6), N7);
		double Nmax_ = Math.max(Math.max(N5_, N6_), N7);
		for (ForcesCombination combination : combinations) {
			double N = combination.getN();
			if (N<N0 || N>Nmax || N>Nmax_) return true;
		}
		return false;
	}
	
	public void mainKNGAlgorithm(ArrayList<ForcesCombination> combinations) {
		prepareValues();
		prepareXValues();
		aS = AsBase;
		mainFor(combinations);
	}
	
	protected boolean lastStepDone() {
		if (IST==2) return true;
		else {
			IST=2;
			if (aS!=aSMin) aS = 0.9*aS;
			return false;
		}
	}
	
	protected void mainFor(ArrayList<ForcesCombination> combinations) {
		setAsMinAndAs(combinations);
		setSValues();
		int i = 0;
		while(true) {
			i++;
			System.out.println("Iteracja nr: " + i);
			setAsValues();
			countNUltimate();
			while (checkNEdLessN0OrGreaterThanNMax(combinations)) {
				increaseAS();
				setSValues();
				setAsValues();
				countNUltimate();
			}
			for (ForcesCombination combination: combinations) {
				findUpParts(combination);
				findDownParts(combination);
				
			}
			
			if (isConditionMet(combinations)) {
				if(lastStepDone()) break;
				else setSValues();
			}
			else {
				increaseS();
				if (s>(1.0-sMin)) {
					increaseAS();
					setSValues();
				}
			}
		}
	}
	
	protected boolean isConditionMet(ArrayList<ForcesCombination> combinations) {
		for (ForcesCombination combination : combinations) {
			if (!(combination.getmRd()>=combination.getM() && combination.getmRd_()<=combination.getM())) return false;
		}
		return true;
	}
	
	protected void increaseAS() {
		if(IST==1) aS += 0.02*aS;
		else aS += 0.001*aS;
	}
	
	protected void increaseS() {
		if(IST==1) s += 0.05;
		else s += 0.005;
	}

	public double getAS1() {
		return AS1;
	}

	public double getAS2() {
		return AS2;
	}
	
	
}
