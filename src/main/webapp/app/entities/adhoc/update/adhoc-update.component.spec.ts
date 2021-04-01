jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AdhocService } from '../service/adhoc.service';
import { IAdhoc, Adhoc } from '../adhoc.model';

import { AdhocUpdateComponent } from './adhoc-update.component';

describe('Component Tests', () => {
  describe('Adhoc Management Update Component', () => {
    let comp: AdhocUpdateComponent;
    let fixture: ComponentFixture<AdhocUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let adhocService: AdhocService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AdhocUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AdhocUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdhocUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      adhocService = TestBed.inject(AdhocService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const adhoc: IAdhoc = { id: 'CBA' };

        activatedRoute.data = of({ adhoc });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(adhoc));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const adhoc = { id: 'ABC' };
        spyOn(adhocService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ adhoc });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: adhoc }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(adhocService.update).toHaveBeenCalledWith(adhoc);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const adhoc = new Adhoc();
        spyOn(adhocService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ adhoc });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: adhoc }));
        saveSubject.complete();

        // THEN
        expect(adhocService.create).toHaveBeenCalledWith(adhoc);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const adhoc = { id: 'ABC' };
        spyOn(adhocService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ adhoc });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(adhocService.update).toHaveBeenCalledWith(adhoc);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
