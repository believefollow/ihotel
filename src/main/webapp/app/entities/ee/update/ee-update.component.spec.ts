jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EeService } from '../service/ee.service';
import { IEe, Ee } from '../ee.model';

import { EeUpdateComponent } from './ee-update.component';

describe('Component Tests', () => {
  describe('Ee Management Update Component', () => {
    let comp: EeUpdateComponent;
    let fixture: ComponentFixture<EeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let eeService: EeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      eeService = TestBed.inject(EeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const ee: IEe = { id: 456 };

        activatedRoute.data = of({ ee });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(ee));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ee = { id: 123 };
        spyOn(eeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ee });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ee }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(eeService.update).toHaveBeenCalledWith(ee);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ee = new Ee();
        spyOn(eeService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ee });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ee }));
        saveSubject.complete();

        // THEN
        expect(eeService.create).toHaveBeenCalledWith(ee);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ee = { id: 123 };
        spyOn(eeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ee });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(eeService.update).toHaveBeenCalledWith(ee);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
